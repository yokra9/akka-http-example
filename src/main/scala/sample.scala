import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object main {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-sample-app")

    // GET /indexでリクエストのURLパラメータとUserAgentを返却する
    val route =
      (get & pathPrefix("index") & extractUri & headerValueByName(
        "User-Agent"
      )) { (uri, ua) =>
        logRequestResult("/index", Logging.InfoLevel) {
          complete(s"param: ${uri.query().toMap}, user-agent: ${ua}}")
        }
      }

    val host = sys.props.get("http.host") getOrElse "0.0.0.0"
    val port = sys.props.get("http.port").fold(8080) { _.toInt }

    val f = Http().newServerAt(host, port).bind(route)

    println(s"server at [$host:$port]")

    Await.ready(f, Duration.Inf)
  }
}
