LimeAcademy-Entry-Task-Wallet

This is a simple wallet application created as an entry task for the LimeAcademy internship program. The application allows users to manage their digital wallets, make transactions between wallets, and create new wallets.

Getting Started

To get started with the LimeAcademy-Entry-Task-Wallet project, follow these steps:

Clone the repository to your local machine using git clone https://github.com/GGerginov/LimeAcademy-Entry-Task-Wallet.git
Open the project in your preferred IDE (such as IntelliJ or Eclipse)
Build the project using the provided build tool (such as Maven or Gradle)
Run the project using the provided run configuration or command
Usage

The LimeAcademy-Entry-Task-Wallet application provides a REST API for managing wallets. Here are the available endpoints:

GET /wallet/all
Returns a list of all wallets in the system.

GET /wallet/{address}
Returns the wallet with the given address.

POST /api/v1/create-wallet
Creates a new wallet

POST /api/v1/make-transaction
Creates a new transaction


Technologies

The LimeAcademy-Entry-Task-Wallet project uses the following technologies:

Java 17
Spring Boot
Spring Data JPA
ModelMapper
Lombok
Contributing

Contributions to the LimeAcademy-Entry-Task-Wallet project are welcome! If you have an idea for a feature or a bug fix, please feel free to open a pull request.

License

The LimeAcademy-Entry-Task-Wallet project is licensed under the MIT License. See the LICENSE file for more information.
