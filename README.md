xke-akka-http
=========================

[![License][license-badge]][license-url]

### Setup

1. Clone the repo: `git clone https://github.com/charlescd/xke-akka-http.git`
2. `bin/activator` to enter in the sbt console and then:
    - `test` to run the test suite
    - `re-start` to run the server
    - `re-stop` to stop it

 If you only need to compile, in the sbt console:
- `compile` to compile the code
- `test:compile` to compile the tests

### Exercise

The exercise in split in steps. Each step has a dedicated branch *step-X*. The branch master is the step 0.
Navigate between steps with `git checkout step-X`.

1. The step 0 asks you to complete the files *Main.scala* and *Routes.scala*. Then, run the tests and check if it's all green.
2. Once done, go to step-1 and complete the *Routes.scala* etc.
3. The file *Main.scala* only needs to be modified in step 0.

[license-badge]: https://img.shields.io/badge/License-Apache%202-blue.svg?style=flat-square
[license-url]: LICENSE.txt
