<p align="center">
    <h3 align="center">ASE program design - Cookbook</h3>
    <p align="center">
        <a target="_blank" href="./.github/workflows/ci.yml"><img src="https://github.com/Niklas-23/ase-programmentwurf/actions/workflows/ci.yml/badge.svg"></a>
        <a target="_blank" href="https://sonarcloud.io/summary/new_code?id=Niklas-23_ase-programmentwurf"><img src="https://sonarcloud.io/api/project_badges/measure?project=Niklas-23_ase-programmentwurf&metric=alert_status"></a>
    </p>
</p>

---

Cookbook is an application for managing recipes and searching for other user's recipes.

### Execution

To run the application you can download the .jar [file](Cookbook-1.0.jar).

### Testing

To run all tests navigate to the project root folder and run the command ```mvn test```.

### Compiling

To compile the project and create the .jar file, navigate to the project root folder and run the command ```mvn clean compile assembly:single```. A .jar file is created for each sub-project and the .jar file in the plugin layer is the executable Cookbook application.
