# Chatbot Application

With the recent advancements in On Device AI technology, chipset manufacturers are increasingly releasing models equipped with Neural Processing Units (NPUs) that efficiently handle computations. These technological changes significantly enhance the performance of AI applications and create new opportunities across various fields. In line with this trend, our project decided to leverage the benefits of NPUs to develop a locally running Chatbot. The reasons are as follows:

1. **Privacy Protection**:
   By processing data locally, user data is not transmitted to external servers, enhancing privacy protection. This provides users with a secure environment to use the Chatbot.

2. **Response Speed**:
   A locally running Chatbot utilizing NPUs can offer faster response times compared to cloud-based services. This significantly improves user experience by providing immediate responses to user queries.

3. **Cost Reduction**:
   It reduces costs associated with using cloud services. A locally processed Chatbot offers a more economical solution in the long run, with reduced maintenance costs as well.

For these reasons, we aim to maximize the utilization of On Device AI technology and the performance of NPUs to develop a Chatbot that operates efficiently in a local environment. This Chatbot aims to provide users with safe, fast, and economical AI services.


## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)

## Features

- **Interactive User Interface**: Built with JavaFX for a modern and responsive UI.
- **Asynchronous Processing**: Uses CompletableFuture and TokenStream for non-blocking operations.
- **Real-time Responses**: Provides real-time responses from the language model.
- **Easy Integration**: Seamless integration with Ollama using Langchain4j.

## Technologies Used

### Gradle

Gradle is an open-source build automation tool that is used for dependency management and project build automation in this project.

- **Dependency Management**: Easily add and manage project dependencies.
- **Build Automation**: Automate tasks such as compilation, testing, and packaging.
- **Extensibility**: Extendable through plugins for additional functionality.

### JavaFX

JavaFX is a powerful GUI toolkit for Java that provides a wide range of UI controls, CSS styling, and FXML support.

- **Rich UI Components**: Buttons, labels, text fields, tables, etc.
- **CSS Styling**: Customize the look and feel with CSS.
- **FXML Support**: Declarative layout definition using XML.
- **Media Integration**: Support for audio and video playback.

### Ollama (LLM Engine)

Ollama is a service that provides state-of-the-art large language models for natural language processing tasks.

- **Language Model**: High-performance models for understanding and generating text.
- **API Communication**: Communicate with the model using RESTful APIs.
- **Natural Language Processing**: Perform tasks such as NLU (Natural Language Understanding) and NLG (Natural Language Generation).

### Langchain4j

Langchain4j is a Java library that facilitates easy integration with language models and provides support for asynchronous stream processing.

- **Asynchronous Processing**: Handle language model communication without blocking the main thread.
- **Stream Processing**: Process responses in real-time as they are received.
- **Easy Integration**: Simplify integration with various language models.

## Project Structure

```
src
├── main
│   ├── java
│   │   └── dev
│   │       ├── Main.java
│   │       ├── service
│   │       │   ├── ChatService.java
│   │       │   ├── ModelCommunication.java
│   │       │   └── UserStreamCommunication.java
│   │       └── view
│   │           ├── ChatController.java
│   │           └── ChatUI.java
│   └── resources
│       └── styles.css
```
- **Main.java**: The entry point of the application.
- **service/**: Contains the core service classes for handling language model communication.
  - ChatService.java: Manages communication with the language model.
  - ModelCommunication.java: Interface for model communication.
  - UserStreamCommunication.java: Interface for user stream communication.
- **view/**: Contains the classes for the user interface.
  - ChatController.java: Handles user input and updates the UI with responses.
  - ChatUI.java: Initializes and sets up the JavaFX UI components.
- **resources/**: Contains additional resources such as CSS for styling.

## Installation

To get started with this project, follow these steps:

1. **Download LLM Service Engine**:
   [https://ollama.com/download](https://ollama.com/download)

2. **Download LLM Models**:
   Visit [https://ollama.com/library](https://ollama.com/library) to browse and download the desired models.
   For example, to download and run the "aya" model, you can use the following command:
   ```sh
   ollama run aya
2. **Clone the repository**:
   ```sh
   git clone https://github.com/PerfectDreamComeTrue/Chatbot-with-Ollama.git
   cd Chatbot-with-Ollama
3. **Install dependencies**:
   ```sh
   gradle build

## Usage
1. **Start ollama**
   ```sh 
   ollama serve
2. **Run the application**:
   ```sh
   gradle run
