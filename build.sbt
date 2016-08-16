enablePlugins(GatlingPlugin)

scalaVersion := "2.11.8"

name := "ScalaMetricsTest"

version := "1.0"


scalacOptions := Seq(
   "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
   "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies ++= {
   val akkaV = "2.3.9"
   val sprayV = "1.3.3"
   Seq(
      "io.spray"            %%  "spray-can"     % sprayV,
      "io.spray"            %%  "spray-routing" % sprayV,
      "io.spray" %%  "spray-json" % "1.3.2",
      "io.spray"            %%  "spray-testkit" % sprayV  % "test",
      "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
      "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
      "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
      "nl.grons" %% "metrics-scala" % "3.5.4_a2.3",
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test",
      "io.gatling"            % "gatling-test-framework"    % "2.2.2" % "test",
      "com.twitter" % "util-core_2.11" % "6.35.0"
   )
}
