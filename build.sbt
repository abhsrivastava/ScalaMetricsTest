name := "ScalaMetricsTest"

version := "1.0"

scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
   val akkaV = "2.3.9"
   val sprayV = "1.3.3"
   enablePlugins(GatlingPlugin)
   Seq(
      "io.spray"            %%  "spray-can"     % sprayV,
      "io.spray"            %%  "spray-routing" % sprayV,
      "io.spray" %%  "spray-json" % "1.3.2",
      "io.spray"            %%  "spray-testkit" % sprayV  % "test",
      "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
      "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
      "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
      "nl.grons" %% "metrics-scala" % "3.5.4_a2.3",
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.5" % "test",
      "io.gatling"            % "gatling-test-framework"    % "2.1.5" % "test"
   )
}
