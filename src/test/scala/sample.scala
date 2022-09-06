import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps
import org.scalatest.funsuite.AsyncFunSuite

class MainTests extends AsyncFunSuite {

  var step = 0
  test(s"${step}: サーバを起動（main）") {
    Main.main(Array(""))

    assert(true)
  }

  step += 1
  test(s"${step}: ステータスコードが200であること") {
    implicit val system = ActorSystem(Behaviors.empty, s"step${step}")
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = "http://localhost:8080/index?q=1"))

    responseFuture.map(response => {
      println(response.status)
      assert(response.status.intValue == 200)
    })
  }

  step += 1
  test(s"${step}: レスポンス本文が正しいこと（クエリあり）") {
    implicit val system = ActorSystem(Behaviors.empty, s"step${step}")
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = "http://localhost:8080/index?q=1"))

    val response = Await.result(responseFuture, 1000 millis)
    Unmarshal(response)
      .to[String]
      .map(str => {
        println(str)
        assert(
          str.equals("param: Map(q -> 1), user-agent: akka-http}")
        )
      })
  }

  step += 1
  test(s"${step}: レスポンス本文が正しいこと（クエリなし）") {
    implicit val system = ActorSystem(Behaviors.empty, s"step${step}")
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = "http://localhost:8080/index"))

    val response = Await.result(responseFuture, 1000 millis)
    Unmarshal(response)
      .to[String]
      .map(str => {
        println(str)
        assert(
          str.equals("param: Map(), user-agent: akka-http}")
        )
      })
  }

  step += 1
  test(s"${step}: 存在しないページのステータスコードが404であること") {
    implicit val system = ActorSystem(Behaviors.empty, s"step${step}")
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = "http://localhost:8080/"))

    responseFuture.map(response => {
      println(response.status)
      assert(response.status.intValue == 404)
    })
  }

  step += 1
  test(s"${step}: サーバを起動（Port指定）") {
    sys.props("http.port") = "8081"
    val serverbinding = Await.result(Main.start(), 1000 millis)

    assert(serverbinding.localAddress.getPort() == 8081)
  }

  step += 1
  test(s"${step}: 環境変数でポート指定ができること") {
    implicit val system = ActorSystem(Behaviors.empty, s"step${step}")
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = "http://localhost:8081/index?q=1"))

    responseFuture.map(response => {
      println(response.status)
      assert(response.status.intValue == 200)
    })
  }

}
