package com.example.apichallenge.service;

import com.example.apichallenge.client.GoogleBooksClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceCachingTest {
    @Autowired
    private BookService bookService;

    @MockBean
    private GoogleBooksClient googleBooksClient;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testBookServiceCaching() {
        // Given
        String query = "spring+boot";
        when(googleBooksClient.searchBooks(query, 5)).thenReturn("{\n" +
                "  \"kind\": \"books#volumes\",\n" +
                "  \"totalItems\": 1000,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"kind\": \"books#volume\",\n" +
                "      \"id\": \"RQUaEAAAQBAJ\",\n" +
                "      \"etag\": \"MA26wGfV1hc\",\n" +
                "      \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/RQUaEAAAQBAJ\",\n" +
                "      \"volumeInfo\": {\n" +
                "        \"title\": \"Spring Boot: Up and Running\",\n" +
                "        \"authors\": [\n" +
                "          \"Mark Heckler\"\n" +
                "        ],\n" +
                "        \"publisher\": \"O'Reilly Media\",\n" +
                "        \"publishedDate\": \"2021-02-05\",\n" +
                "        \"description\": \"With over 75 million downloads per month, Spring Boot is the most widely used Java framework available. Its ease and power have revolutionized application development from monoliths to microservices. Yet Spring Boot's simplicity can also be confounding. How do developers learn enough to be productive immediately? This practical book shows you how to use this framework to write successful mission-critical applications. Mark Heckler from VMware, the company behind Spring, guides you through Spring Boot's architecture and approach, covering topics such as debugging, testing, and deployment. If you want to develop cloud native Java or Kotlin applications with Spring Boot rapidly and effectively--using reactive programming, building APIs, and creating database access of all kinds--this book is for you. Learn how Spring Boot simplifies cloud native application development and deployment Build reactive applications and extend communication across the network boundary to create distributed systems Understand how Spring Boot's architecture and approach increase developer productivity and application portability Deploy Spring Boot applications for production workloads rapidly and reliably Monitor application and system health for optimal performance and reliability Debug, test, and secure cloud-based applications painlessly\",\n" +
                "        \"industryIdentifiers\": [\n" +
                "          {\n" +
                "            \"type\": \"ISBN_13\",\n" +
                "            \"identifier\": \"9781492076957\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"ISBN_10\",\n" +
                "            \"identifier\": \"1492076953\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"readingModes\": {\n" +
                "          \"text\": false,\n" +
                "          \"image\": true\n" +
                "        },\n" +
                "        \"pageCount\": 330,\n" +
                "        \"printType\": \"BOOK\",\n" +
                "        \"categories\": [\n" +
                "          \"Computers\"\n" +
                "        ],\n" +
                "        \"maturityRating\": \"NOT_MATURE\",\n" +
                "        \"allowAnonLogging\": false,\n" +
                "        \"contentVersion\": \"0.1.1.0.preview.1\",\n" +
                "        \"panelizationSummary\": {\n" +
                "          \"containsEpubBubbles\": false,\n" +
                "          \"containsImageBubbles\": false\n" +
                "        },\n" +
                "        \"imageLinks\": {\n" +
                "          \"smallThumbnail\": \"http://books.google.com/books/content?id=RQUaEAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
                "          \"thumbnail\": \"http://books.google.com/books/content?id=RQUaEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
                "        },\n" +
                "        \"language\": \"en\",\n" +
                "        \"previewLink\": \"http://books.google.com/books?id=RQUaEAAAQBAJ&printsec=frontcover&dq=spring+boot&hl=&cd=1&source=gbs_api\",\n" +
                "        \"infoLink\": \"http://books.google.com/books?id=RQUaEAAAQBAJ&dq=spring+boot&hl=&source=gbs_api\",\n" +
                "        \"canonicalVolumeLink\": \"https://books.google.com/books/about/Spring_Boot_Up_and_Running.html?hl=&id=RQUaEAAAQBAJ\"\n" +
                "      },\n" +
                "      \"saleInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"saleability\": \"NOT_FOR_SALE\",\n" +
                "        \"isEbook\": false\n" +
                "      },\n" +
                "      \"accessInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"viewability\": \"PARTIAL\",\n" +
                "        \"embeddable\": true,\n" +
                "        \"publicDomain\": false,\n" +
                "        \"textToSpeechPermission\": \"ALLOWED\",\n" +
                "        \"epub\": {\n" +
                "          \"isAvailable\": false\n" +
                "        },\n" +
                "        \"pdf\": {\n" +
                "          \"isAvailable\": true\n" +
                "        },\n" +
                "        \"webReaderLink\": \"http://play.google.com/books/reader?id=RQUaEAAAQBAJ&hl=&source=gbs_api\",\n" +
                "        \"accessViewStatus\": \"SAMPLE\",\n" +
                "        \"quoteSharingAllowed\": false\n" +
                "      },\n" +
                "      \"searchInfo\": {\n" +
                "        \"textSnippet\": \"Yet Spring Boot&#39;s simplicity can also be confounding. How do developers learn enough to be productive immediately? This practical book shows you how to use this framework to write successful mission-critical applications.\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"kind\": \"books#volume\",\n" +
                "      \"id\": \"lMlzEAAAQBAJ\",\n" +
                "      \"etag\": \"hv3DHwRFJSQ\",\n" +
                "      \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/lMlzEAAAQBAJ\",\n" +
                "      \"volumeInfo\": {\n" +
                "        \"title\": \"Spring Boot in Practice\",\n" +
                "        \"authors\": [\n" +
                "          \"Somnath Musib\"\n" +
                "        ],\n" +
                "        \"publisher\": \"Simon and Schuster\",\n" +
                "        \"publishedDate\": \"2022-07-12\",\n" +
                "        \"description\": \"Spring Boot in Practice is full of practical recipes for common development problems in Spring Boot. Author Somnath Musib has spent years building applications with Spring, and he shares that extensive experience in this focused guide. You’ll master techniques for using Spring Data, Spring Security, and other Spring-centric solutions. Learn how to work with Spring Boot and Kotlin, handling connections for multiple platforms, and how Spring Boot can simplify building microservices and APIs. Each recipe is built around a real-world problem, complete with a full solution and thoughtful discussion.\",\n" +
                "        \"industryIdentifiers\": [\n" +
                "          {\n" +
                "            \"type\": \"ISBN_13\",\n" +
                "            \"identifier\": \"9781617298813\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"ISBN_10\",\n" +
                "            \"identifier\": \"1617298816\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"readingModes\": {\n" +
                "          \"text\": false,\n" +
                "          \"image\": false\n" +
                "        },\n" +
                "        \"pageCount\": 582,\n" +
                "        \"printType\": \"BOOK\",\n" +
                "        \"categories\": [\n" +
                "          \"Computers\"\n" +
                "        ],\n" +
                "        \"averageRating\": 1,\n" +
                "        \"ratingsCount\": 1,\n" +
                "        \"maturityRating\": \"NOT_MATURE\",\n" +
                "        \"allowAnonLogging\": false,\n" +
                "        \"contentVersion\": \"0.0.1.0.preview.0\",\n" +
                "        \"panelizationSummary\": {\n" +
                "          \"containsEpubBubbles\": false,\n" +
                "          \"containsImageBubbles\": false\n" +
                "        },\n" +
                "        \"imageLinks\": {\n" +
                "          \"smallThumbnail\": \"http://books.google.com/books/content?id=lMlzEAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
                "          \"thumbnail\": \"http://books.google.com/books/content?id=lMlzEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
                "        },\n" +
                "        \"language\": \"en\",\n" +
                "        \"previewLink\": \"http://books.google.com/books?id=lMlzEAAAQBAJ&printsec=frontcover&dq=spring+boot&hl=&cd=2&source=gbs_api\",\n" +
                "        \"infoLink\": \"http://books.google.com/books?id=lMlzEAAAQBAJ&dq=spring+boot&hl=&source=gbs_api\",\n" +
                "        \"canonicalVolumeLink\": \"https://books.google.com/books/about/Spring_Boot_in_Practice.html?hl=&id=lMlzEAAAQBAJ\"\n" +
                "      },\n" +
                "      \"saleInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"saleability\": \"NOT_FOR_SALE\",\n" +
                "        \"isEbook\": false\n" +
                "      },\n" +
                "      \"accessInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"viewability\": \"PARTIAL\",\n" +
                "        \"embeddable\": true,\n" +
                "        \"publicDomain\": false,\n" +
                "        \"textToSpeechPermission\": \"ALLOWED_FOR_ACCESSIBILITY\",\n" +
                "        \"epub\": {\n" +
                "          \"isAvailable\": false\n" +
                "        },\n" +
                "        \"pdf\": {\n" +
                "          \"isAvailable\": false\n" +
                "        },\n" +
                "        \"webReaderLink\": \"http://play.google.com/books/reader?id=lMlzEAAAQBAJ&hl=&source=gbs_api\",\n" +
                "        \"accessViewStatus\": \"SAMPLE\",\n" +
                "        \"quoteSharingAllowed\": false\n" +
                "      },\n" +
                "      \"searchInfo\": {\n" +
                "        \"textSnippet\": \"This book provides a rich collection of techniques to help you get the most out of Spring Boot. About the book Spring Boot in Practice is a cookbook-style guide to Spring application development.\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"kind\": \"books#volume\",\n" +
                "      \"id\": \"nIF8DwAAQBAJ\",\n" +
                "      \"etag\": \"r4euPvzCsbE\",\n" +
                "      \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/nIF8DwAAQBAJ\",\n" +
                "      \"volumeInfo\": {\n" +
                "        \"title\": \"Spring Boot 2 Recipes\",\n" +
                "        \"subtitle\": \"A Problem-Solution Approach\",\n" +
                "        \"authors\": [\n" +
                "          \"Marten Deinum\"\n" +
                "        ],\n" +
                "        \"publisher\": \"Apress\",\n" +
                "        \"publishedDate\": \"2018-11-28\",\n" +
                "        \"description\": \"Solve all your Spring Boot 2 problems using complete and real-world code examples. When you start a new project, you’ll be able to copy the code and configuration files from this book, and then modify them for your needs. This can save you a great deal of work over creating a project from scratch. Using a problem-solution approach, Spring Boot 2 Recipes quickly introduces you to Pivotal's Spring Boot 2 micro-framework, then dives into code snippets on how to apply and integrate Spring Boot 2 with the Spring MVC web framework, Spring Web Sockets, and microservices. You'll also get solutions to common problems with persistence, integrating Spring Boot with batch processing, algorithmic programming via Spring Batch, and much more. Other recipes cover topics such as using and integrating Boot with Spring's enterprise services, Spring Integration, testing, monitoring and more. What You'll LearnGet reusable code recipes and snippets for the Spring Boot 2 micro-framework Discover how Spring Boot 2 integrates with other Spring APIs, tools, and frameworks Access Spring MVC and the new Spring Web Sockets for simpler web development Work with microservices for web services development and integration with your Spring Boot applications Add persistence and a data tier seamlessly to make your Spring Boot web application do more Integrate enterprise services to create a more complex Java application using Spring Boot Who This Book Is For Experienced Java and Spring programmers.\",\n" +
                "        \"industryIdentifiers\": [\n" +
                "          {\n" +
                "            \"type\": \"ISBN_13\",\n" +
                "            \"identifier\": \"9781484239636\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"ISBN_10\",\n" +
                "            \"identifier\": \"1484239636\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"readingModes\": {\n" +
                "          \"text\": true,\n" +
                "          \"image\": true\n" +
                "        },\n" +
                "        \"pageCount\": 346,\n" +
                "        \"printType\": \"BOOK\",\n" +
                "        \"categories\": [\n" +
                "          \"Computers\"\n" +
                "        ],\n" +
                "        \"maturityRating\": \"NOT_MATURE\",\n" +
                "        \"allowAnonLogging\": false,\n" +
                "        \"contentVersion\": \"preview-1.0.0\",\n" +
                "        \"panelizationSummary\": {\n" +
                "          \"containsEpubBubbles\": false,\n" +
                "          \"containsImageBubbles\": false\n" +
                "        },\n" +
                "        \"imageLinks\": {\n" +
                "          \"smallThumbnail\": \"http://books.google.com/books/content?id=nIF8DwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
                "          \"thumbnail\": \"http://books.google.com/books/content?id=nIF8DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
                "        },\n" +
                "        \"language\": \"en\",\n" +
                "        \"previewLink\": \"http://books.google.com/books?id=nIF8DwAAQBAJ&printsec=frontcover&dq=spring+boot&hl=&cd=3&source=gbs_api\",\n" +
                "        \"infoLink\": \"http://books.google.com/books?id=nIF8DwAAQBAJ&dq=spring+boot&hl=&source=gbs_api\",\n" +
                "        \"canonicalVolumeLink\": \"https://books.google.com/books/about/Spring_Boot_2_Recipes.html?hl=&id=nIF8DwAAQBAJ\"\n" +
                "      },\n" +
                "      \"saleInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"saleability\": \"NOT_FOR_SALE\",\n" +
                "        \"isEbook\": false\n" +
                "      },\n" +
                "      \"accessInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"viewability\": \"PARTIAL\",\n" +
                "        \"embeddable\": true,\n" +
                "        \"publicDomain\": false,\n" +
                "        \"textToSpeechPermission\": \"ALLOWED\",\n" +
                "        \"epub\": {\n" +
                "          \"isAvailable\": true,\n" +
                "          \"acsTokenLink\": \"http://books.google.com/books/download/Spring_Boot_2_Recipes-sample-epub.acsm?id=nIF8DwAAQBAJ&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" +
                "        },\n" +
                "        \"pdf\": {\n" +
                "          \"isAvailable\": true,\n" +
                "          \"acsTokenLink\": \"http://books.google.com/books/download/Spring_Boot_2_Recipes-sample-pdf.acsm?id=nIF8DwAAQBAJ&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" +
                "        },\n" +
                "        \"webReaderLink\": \"http://play.google.com/books/reader?id=nIF8DwAAQBAJ&hl=&source=gbs_api\",\n" +
                "        \"accessViewStatus\": \"SAMPLE\",\n" +
                "        \"quoteSharingAllowed\": false\n" +
                "      },\n" +
                "      \"searchInfo\": {\n" +
                "        \"textSnippet\": \"What You&#39;ll LearnGet reusable code recipes and snippets for the Spring Boot 2 micro-framework Discover how Spring Boot 2 integrates with other Spring APIs, tools, and frameworks Access Spring MVC and the new Spring Web Sockets for simpler ...\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"kind\": \"books#volume\",\n" +
                "      \"id\": \"lMlzEAAAQBAJ\",\n" +
                "      \"etag\": \"3FTxMKueLoE\",\n" +
                "      \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/lMlzEAAAQBAJ\",\n" +
                "      \"volumeInfo\": {\n" +
                "        \"title\": \"Spring Boot in Practice\",\n" +
                "        \"authors\": [\n" +
                "          \"Somnath Musib\"\n" +
                "        ],\n" +
                "        \"publisher\": \"Simon and Schuster\",\n" +
                "        \"publishedDate\": \"2022-07-12\",\n" +
                "        \"description\": \"Spring Boot in Practice is full of practical recipes for common development problems in Spring Boot. Author Somnath Musib has spent years building applications with Spring, and he shares that extensive experience in this focused guide. You’ll master techniques for using Spring Data, Spring Security, and other Spring-centric solutions. Learn how to work with Spring Boot and Kotlin, handling connections for multiple platforms, and how Spring Boot can simplify building microservices and APIs. Each recipe is built around a real-world problem, complete with a full solution and thoughtful discussion.\",\n" +
                "        \"industryIdentifiers\": [\n" +
                "          {\n" +
                "            \"type\": \"ISBN_13\",\n" +
                "            \"identifier\": \"9781617298813\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"ISBN_10\",\n" +
                "            \"identifier\": \"1617298816\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"readingModes\": {\n" +
                "          \"text\": false,\n" +
                "          \"image\": false\n" +
                "        },\n" +
                "        \"pageCount\": 582,\n" +
                "        \"printType\": \"BOOK\",\n" +
                "        \"categories\": [\n" +
                "          \"Computers\"\n" +
                "        ],\n" +
                "        \"averageRating\": 1,\n" +
                "        \"ratingsCount\": 1,\n" +
                "        \"maturityRating\": \"NOT_MATURE\",\n" +
                "        \"allowAnonLogging\": false,\n" +
                "        \"contentVersion\": \"0.0.1.0.preview.0\",\n" +
                "        \"panelizationSummary\": {\n" +
                "          \"containsEpubBubbles\": false,\n" +
                "          \"containsImageBubbles\": false\n" +
                "        },\n" +
                "        \"imageLinks\": {\n" +
                "          \"smallThumbnail\": \"http://books.google.com/books/content?id=lMlzEAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
                "          \"thumbnail\": \"http://books.google.com/books/content?id=lMlzEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
                "        },\n" +
                "        \"language\": \"en\",\n" +
                "        \"previewLink\": \"http://books.google.com/books?id=lMlzEAAAQBAJ&printsec=frontcover&dq=spring+boot&hl=&cd=4&source=gbs_api\",\n" +
                "        \"infoLink\": \"http://books.google.com/books?id=lMlzEAAAQBAJ&dq=spring+boot&hl=&source=gbs_api\",\n" +
                "        \"canonicalVolumeLink\": \"https://books.google.com/books/about/Spring_Boot_in_Practice.html?hl=&id=lMlzEAAAQBAJ\"\n" +
                "      },\n" +
                "      \"saleInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"saleability\": \"NOT_FOR_SALE\",\n" +
                "        \"isEbook\": false\n" +
                "      },\n" +
                "      \"accessInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"viewability\": \"PARTIAL\",\n" +
                "        \"embeddable\": true,\n" +
                "        \"publicDomain\": false,\n" +
                "        \"textToSpeechPermission\": \"ALLOWED_FOR_ACCESSIBILITY\",\n" +
                "        \"epub\": {\n" +
                "          \"isAvailable\": false\n" +
                "        },\n" +
                "        \"pdf\": {\n" +
                "          \"isAvailable\": false\n" +
                "        },\n" +
                "        \"webReaderLink\": \"http://play.google.com/books/reader?id=lMlzEAAAQBAJ&hl=&source=gbs_api\",\n" +
                "        \"accessViewStatus\": \"SAMPLE\",\n" +
                "        \"quoteSharingAllowed\": false\n" +
                "      },\n" +
                "      \"searchInfo\": {\n" +
                "        \"textSnippet\": \"This book provides a rich collection of techniques to help you get the most out of Spring Boot. About the book Spring Boot in Practice is a cookbook-style guide to Spring application development.\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"kind\": \"books#volume\",\n" +
                "      \"id\": \"01NxDwAAQBAJ\",\n" +
                "      \"etag\": \"EVBPZOGD9eU\",\n" +
                "      \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/01NxDwAAQBAJ\",\n" +
                "      \"volumeInfo\": {\n" +
                "        \"title\": \"Developing Java Applications with Spring and Spring Boot\",\n" +
                "        \"subtitle\": \"Practical Spring and Spring Boot solutions for building effective applications\",\n" +
                "        \"authors\": [\n" +
                "          \"Claudio Eduardo de Oliveira\",\n" +
                "          \"Greg L. Turnquist\",\n" +
                "          \"Alex Antonov\"\n" +
                "        ],\n" +
                "        \"publisher\": \"Packt Publishing Ltd\",\n" +
                "        \"publishedDate\": \"2018-10-04\",\n" +
                "        \"description\": \"An end-to-end software development guide for the Java eco-system using the most advanced frameworks: Spring and Spring Boot. Learn the complete workflow by building projects and solving problems. About This BookLearn reactive programming by implementing a reactive application with Spring WebFluxCreate a robust and scalable messaging application with Spring messaging supportGet up-to-date with the defining characteristics of Spring Boot 2.0 in Spring Framework 5Learn about developer tools, AMQP messaging, WebSockets, security, MongoDB data access, REST, and moreThis collection of effective recipes serves as guidelines for Spring Boot application developmentWho This Book Is For Java developers wanting to build production-grade applications using the newest popular Spring tools for a rich end-to-end application development experience. What You Will LearnGet to know the Spring Boot and understand how it makes creating robust applications extremely simpleUnderstand how Spring Data helps us add persistence in MongoDB and SQL databasesImplement a websocket to add interactive behaviors in your applicationsCreate powerful, production-grade applications and services with minimal fussUse custom metrics to track the number of messages published and consumedBuild anything from lightweight unit tests to fully running embedded web container integration testsLearn effective testing techniques by integrating Cucumber and SpockUse Hashicorp Consul and Netflix Eureka for dynamic Service DiscoveryIn Detail Spring Framework has become the most popular framework for Java development. It not only simplifies software development but also improves developer productivity. This book covers effective ways to develop robust applications in Java using Spring. The course is up made of three modules, each one having a take-away relating to building end-to-end java applications. The first module takes the approach of learning Spring frameworks by building applications.You will learn to build APIs and integrate them with popular fraemworks suh as AngularJS, Spring WebFlux, and Spring Data. You will also learn to build microservices using Spring's support for Kotlin. You will learn about the Reactive paradigm in the Spring architecture using Project Reactor. In the second module, after getting hands-on with Spring, you will learn about the most popular tool in the Spring ecosystem-Spring Boot. You will learn to build applications with Spring Boot, bundle them, and deploy them on the cloud. After learning to build applications with Spring Boot, you will be able to use various tests that are an important part of application development. We also cover the important developer tools such as AMQP messaging, websockets, security, and more. This will give you a good functional understanding of scalable development in the Spring ecosystem with Spring Boot. In the third and final module, you will tackle the most important challenges in Java application development with Spring Boot using practical recipes. Including recipes for testing, deployment, monitoring, and securing your applications. This module will also address the functional and technical requirements for building enterprise applications. By the end of the course you will be comfortable with using Spring and Spring Boot to develop Java applications and will have mastered the intricacies of production-grade applications. Style and approach A simple step-by-step guide with practical examples to help you develop and deploy Spring and Spring Boot applications in the real-world.\",\n" +
                "        \"industryIdentifiers\": [\n" +
                "          {\n" +
                "            \"type\": \"ISBN_13\",\n" +
                "            \"identifier\": \"9781789539134\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"ISBN_10\",\n" +
                "            \"identifier\": \"1789539137\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"readingModes\": {\n" +
                "          \"text\": true,\n" +
                "          \"image\": true\n" +
                "        },\n" +
                "        \"pageCount\": 964,\n" +
                "        \"printType\": \"BOOK\",\n" +
                "        \"categories\": [\n" +
                "          \"Computers\"\n" +
                "        ],\n" +
                "        \"maturityRating\": \"NOT_MATURE\",\n" +
                "        \"allowAnonLogging\": true,\n" +
                "        \"contentVersion\": \"0.0.1.0.preview.3\",\n" +
                "        \"panelizationSummary\": {\n" +
                "          \"containsEpubBubbles\": false,\n" +
                "          \"containsImageBubbles\": false\n" +
                "        },\n" +
                "        \"imageLinks\": {\n" +
                "          \"smallThumbnail\": \"http://books.google.com/books/content?id=01NxDwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
                "          \"thumbnail\": \"http://books.google.com/books/content?id=01NxDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
                "        },\n" +
                "        \"language\": \"en\",\n" +
                "        \"previewLink\": \"http://books.google.com/books?id=01NxDwAAQBAJ&printsec=frontcover&dq=spring+boot&hl=&cd=5&source=gbs_api\",\n" +
                "        \"infoLink\": \"https://play.google.com/store/books/details?id=01NxDwAAQBAJ&source=gbs_api\",\n" +
                "        \"canonicalVolumeLink\": \"https://play.google.com/store/books/details?id=01NxDwAAQBAJ\"\n" +
                "      },\n" +
                "      \"saleInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"saleability\": \"FOR_SALE\",\n" +
                "        \"isEbook\": true,\n" +
                "        \"listPrice\": {\n" +
                "          \"amount\": 83.99,\n" +
                "          \"currencyCode\": \"USD\"\n" +
                "        },\n" +
                "        \"retailPrice\": {\n" +
                "          \"amount\": 67.19,\n" +
                "          \"currencyCode\": \"USD\"\n" +
                "        },\n" +
                "        \"buyLink\": \"https://play.google.com/store/books/details?id=01NxDwAAQBAJ&rdid=book-01NxDwAAQBAJ&rdot=1&source=gbs_api\",\n" +
                "        \"offers\": [\n" +
                "          {\n" +
                "            \"finskyOfferType\": 1,\n" +
                "            \"listPrice\": {\n" +
                "              \"amountInMicros\": 83990000,\n" +
                "              \"currencyCode\": \"USD\"\n" +
                "            },\n" +
                "            \"retailPrice\": {\n" +
                "              \"amountInMicros\": 67190000,\n" +
                "              \"currencyCode\": \"USD\"\n" +
                "            },\n" +
                "            \"giftable\": true\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"accessInfo\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"viewability\": \"PARTIAL\",\n" +
                "        \"embeddable\": true,\n" +
                "        \"publicDomain\": false,\n" +
                "        \"textToSpeechPermission\": \"ALLOWED\",\n" +
                "        \"epub\": {\n" +
                "          \"isAvailable\": true\n" +
                "        },\n" +
                "        \"pdf\": {\n" +
                "          \"isAvailable\": true\n" +
                "        },\n" +
                "        \"webReaderLink\": \"http://play.google.com/books/reader?id=01NxDwAAQBAJ&hl=&source=gbs_api\",\n" +
                "        \"accessViewStatus\": \"SAMPLE\",\n" +
                "        \"quoteSharingAllowed\": false\n" +
                "      },\n" +
                "      \"searchInfo\": {\n" +
                "        \"textSnippet\": \"It not only simplifies software development but also improves developer productivity. This book covers effective ways to develop robust applications in Java using Spring.\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n");


        bookService.getBooks(query); // First call
        bookService.getBooks(query); // Second call


        verify(googleBooksClient, times(1)).searchBooks(query, 5);

        assertNotNull(cacheManager.getCache("books").get(query));
    }
}