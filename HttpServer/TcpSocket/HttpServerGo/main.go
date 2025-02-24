package main

import (
	"fmt"
	"net"
	"os"
	"strings"
)

var commandLineArgs CommandLineArgs

type ServerInfo struct {
	network string
	ip      string
	port    int
}

func main() {
	args := os.Args
	commandLineArgs = parseCommonLineArgs(args)

	serInfo := ServerInfo{network: "tcp", ip: "0.0.0.0", port: 4221}
	listen, err := net.Listen(serInfo.network, fmt.Sprintf("%s:%d", serInfo.ip, serInfo.port))
	errCheck(err)

	defer func(listen net.Listener) {
		err := listen.Close()
		errCheck(err)
	}(listen)

	for {
		connection, err := listen.Accept()
		errCheck(err)

		go acceptConnection(connection)
	}
}

func acceptConnection(conn net.Conn) {
	requestBytes := make([]byte, 1024)
	conn.Read(requestBytes)

	parsedReq, _ := parseRequest(string(requestBytes))

	if parsedReq.requestLine.url == "/" {
		conn.Write([]byte("HTTP/1.1 200 OK\r\n\r\n"))
	} else if strings.HasPrefix(parsedReq.requestLine.url, "/user-agent") {

		userAgentVal := parsedReq.getHeaderByName("User-Agent")
		conn.Write([]byte(fmt.Sprintf("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%v", len(userAgentVal), userAgentVal)))

	} else if strings.HasPrefix(parsedReq.requestLine.url, "/echo") {

		split := strings.Split(parsedReq.requestLine.url, "/")
		conn.Write([]byte(fmt.Sprintf("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%v", len(split[2]), split[2])))

	} else if strings.HasPrefix(parsedReq.requestLine.url, "/files") {

		if parsedReq.requestLine.httpVerb == "POST" {
			split := strings.Split(parsedReq.requestLine.url, "/")
			err := writeToFile(parsedReq.body, commandLineArgs.args["--directory"]+split[2])
			if err != nil {
				connWrite([]byte("HTTP/1.1 404 Not Found\r\n\r\n"), conn)
			} else {
				connWrite([]byte("HTTP/1.1 201 Created\r\n\r\n"), conn)
			}
		} else {
			split := strings.Split(parsedReq.requestLine.url, "/")
			content, err := readFile(commandLineArgs.args["--directory"] + split[2])
			if err != nil {
				connWrite([]byte("HTTP/1.1 404 Not Found\r\n\r\n"), conn)
			} else {
				connWrite([]byte(fmt.Sprintf("HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\nContent-Length: %d\r\n\r\n%v", len(content), string(content))), conn)
			}
		}
	} else {
		connWrite([]byte("HTTP/1.1 404 Not Found\r\n\r\n"), conn)
	}

	defer func(conn net.Conn) {
		err := conn.Close()
		errCheck(err)
	}(conn)
}

func readFile(fileName string) ([]byte, error) {
	f, err := os.ReadFile(fileName)
	if err != nil {
		return make([]byte, 0), err
	}
	return f, err

}

func connWrite(bytes []byte, conn net.Conn) {
	_, err := conn.Write(bytes)
	errCheck(err)
}

func writeToFile(content string, fileName string) error {
	return os.WriteFile(fileName, []byte(content), 0777)
}

func errCheck(err error) {
	if err != nil {
		fmt.Println(err)
	}
}
