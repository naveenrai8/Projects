package main

import (
	"strconv"
	"strings"
)

type HttpRequest struct {
	requestLine RequestLine
	headers     map[string]string
	body        string
}

type RequestLine struct {
	httpVerb    string
	url         string
	httpVersion string
}

func parseRequest(req string) (HttpRequest, error) {
	reqSplit := strings.Split(req, "\r\n")

	httpRequest := HttpRequest{}
	httpRequest.requestLine = parseRequestLine(reqSplit[0])

	httpRequest.headers = convertToMap(reqSplit[1 : len(reqSplit)-1])
	httpRequest.body = reqSplit[len(reqSplit)-1]
	bodySize, _ := strconv.Atoi(httpRequest.headers["Content-Length"])
	httpRequest.body = string(([]byte(httpRequest.body))[:bodySize])
	return httpRequest, nil
}

func (httpReq *HttpRequest) getHeaderByName(headerName string) string {
	return httpReq.headers[headerName]
}

func convertToMap(headers []string) map[string]string {
	headerMap := make(map[string]string)
	for _, val := range headers {

		pair := strings.Split(val, ": ")
		if len(pair) != 2 {
			continue
		}
		headerMap[pair[0]] = pair[1]
	}
	return headerMap
}

func parseRequestLine(req string) RequestLine {
	split := strings.Split(req, " ")

	reqLine := RequestLine{}
	reqLine.httpVerb = split[0]
	reqLine.url = split[1]
	reqLine.httpVersion = split[2]

	return reqLine
}
