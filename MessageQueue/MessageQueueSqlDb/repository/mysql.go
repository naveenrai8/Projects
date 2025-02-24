package repository

import (
	"database/sql"
	"fmt"

	_ "github.com/go-sql-driver/mysql"
)

type DbInformation struct {
	DbName       string
	UserName     string
	Password     string
	DatabaseName string
}

type ConnectionInformation struct {
	db *sql.DB
}

func (c *ConnectionInformation) closeDb() {
	c.db.Close()
}

func GetDbConnection(d *DbInformation) (*ConnectionInformation, error) {

	dbSourceName := d.UserName + ":" + d.Password + "@/" + d.DatabaseName
	fmt.Println("Database source name", dbSourceName)
	db, err := sql.Open(d.DbName, dbSourceName)
	if err != nil {
		panic(err.Error()) // Just for example purpose. You should use proper error handling instead of panic
	}

	// Open doesn't open a connection. Validate DSN data:
	err = db.Ping()
	if err != nil {
		panic(err.Error()) // proper error handling instead of panic in your app
	}
	return &ConnectionInformation{db}, err
}

// mysql connection example https://github.com/go-sql-driver/mysql/wiki/Examples
// https://github.com/go-sql-driver/mysql/?tab=readme-ov-cd ile#features
func Get(ci *ConnectionInformation) {

	db := ci.db
	//db, err := sql.Open("mysql", "root:root1234@/messageQueue")
	//db, err := GetDbConnection(&mysqlConnectionInfo)
	//defer db.Close()

	// Use the DB normally, execute the querys etc
	// Prepare statement for inserting data
	stmtIns, err := db.Prepare("select * from messages") // ? = placeholder
	if err != nil {
		panic(err.Error()) // proper error handling instead of panic in your app
	}
	defer stmtIns.Close()

	res, err := stmtIns.Query()
	if err != nil {
		// panic(err.Error()) // proper error handling instead of panic in your app
		fmt.Println(err)
		return
	}
	var id int
	var message string
	var consumer string
	for res.Next() {
		res.Scan(&id, &message, &consumer)
		//fmt.Println(id, message)
	}
}

func Insert(ci *ConnectionInformation, id string, message string, tableName string) {
	db := ci.db

	// Prepare statement for inserting data
	stmtIns, err := db.Prepare("INSERT INTO " + tableName + " VALUES( ?, ? )") // ? = placeholder
	if err != nil {
		panic(err.Error()) // proper error handling instead of panic in your app
	}
	defer stmtIns.Close() // Close the statement when we leave main() / the program terminates
	_, err = stmtIns.Exec(id, message)
	if err != nil {
		panic(err.Error()) // proper error handling instead of panic in your app
	}
}
