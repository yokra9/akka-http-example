import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.event.Logging
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model._
import org.apache.pekko.http.scaladsl.server.Directives._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

object Main {
  implicit val system: ActorSystem[Any] =
    ActorSystem(Behaviors.empty, "my-sample-app")

  def main(args: Array[String]): Unit = {
    Await.ready(start(), Duration.Inf)
  }

  def start(): Future[Http.ServerBinding] = {

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

    println(s"server at [$host:$port]")

    Http().newServerAt(host, port).bind(route)
  }

}
