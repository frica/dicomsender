# Introduction #

**dicomsender** was created as a side project, to be able to easily send test Dicom data to PACS servers.

The typical use case is:
  * select the folder where your datasets are stored
  * select one file or folder
  * select the server and the port where you want to send the data
  * press Send button

Currently multi-selection of items is not possible. I'm working on it :)

# Configuration #

dicomsender uses two configuration files
  * server.txt, listing the servers that may receive the dicom data
  * config.txt listing the defaut properties of the application

Both files must be located in the same folder as the jar file.