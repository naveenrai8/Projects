package main

import (
	"fmt"
	"sync"
	"time"

	"github.com/google/uuid"
	"github.com/naveenrai8/repository"
	"github.com/oklog/ulid/v2"
)

func main() {
	var wg sync.WaitGroup
	wg.Add(2)
	go dbTest(&wg, "uuidvalues", true)
	go dbTest(&wg, "ulidvalues", false)
	wg.Wait()
}

func dbTest(wg *sync.WaitGroup, tableName string, isUuid bool) {
	defer wg.Done()
	mysqlConnectionInfo := &repository.DbInformation{
		DbName:       "mysql",
		UserName:     "root",
		Password:     "root1234",
		DatabaseName: "uuiduliddb"}

	dbConn, err := repository.GetDbConnection(mysqlConnectionInfo)

	if err != nil {
		panic(err.Error())
	}
	start := time.Now()

	for i := 0; i < 10000; i++ {
		//repository.Get(dbConn)

		// repository.Insert(dbConn, ulid.Make().String(), "Test message ")
		if isUuid {
			repository.Insert(dbConn, uuid.New().String(), "Test message ", tableName)
		} else {
			repository.Insert(dbConn, ulid.Make().String(), "Test message ", tableName)
		}

	}
	fmt.Println("Time taken to insert in table ", tableName, time.Now().Sub(start).Seconds())
}
