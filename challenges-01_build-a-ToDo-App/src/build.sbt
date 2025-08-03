name := "scala-todo-cli"
version := "0.1.0"
scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.17" % Test,
  "com.github.scopt" %% "scopt" % "4.1.0"
)

// Test configuration
Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
Test / parallelExecution := false
Test / logBuffered := false

// Enable colored output
Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oC")

// Assembly configuration for creating executable JAR
assembly / assemblyJarName := "todo-cli.jar"
assembly / mainClass := Some("todo.TodoCLI")
