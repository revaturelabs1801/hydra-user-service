# hydra-user-service
MicroService for a user.
	-The controller is used for receiving requests and sending responses.
	-The service is used for connecting the controller with the repository methods.
		-This service is then used for creating and implementing business logic.
	-The repository is then used for connecting to the repository through JPA methods.
		-These methods mostly include finding users, adding new users, and updating previous users.