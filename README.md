# zettahost-task
Tasks related to working with Amazon.com with Selenium and Java

## Project structure
```
project-root
│
├── src
│   ├── main
│   │   └── java
│   │       └── com/amazon
│   │           ├── components
│   │           │   └── Header.java
│   │           ├── core
│   │           │   └── Base.java
│   │           ├── crawler
│   │           │   └── AmazonCrawler.java
│   │           ├── pages
│   │           │   └── HomePage.java
│   └── test
│       └── java
│           └── yourpackage
│               └── tests
│                   └── AmazonCrawlerTest.java
│
├── target
├── .gitignore
├── pom.xml
└── README.md
```

Main logic for getting all required links is in `Header.java`. 

To run the crawler, run the `AmazonCrawler.java` file.
