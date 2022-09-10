# akka-http-example

[![Test](https://github.com/yokra9/akka-http-example/actions/workflows/Test.yml/badge.svg)](https://github.com/yokra9/akka-http-example/actions/workflows/Test.yml)

* [Scala で作った Web アプリを Dockerize して動かす](https://qiita.com/yokra9/items/dd560305ccb5fc8cd6e1)
* [VSCode 上で快適に Scala を書くための dev container 定義を作ってみた](https://qiita.com/yokra9/items/351b9847c5f1e49a215c)
* [Scala でも Dependabot のように依存ライブラリをアップデートする PR を自動で作成してほしい（Github Actions）](https://qiita.com/yokra9/items/5d80a9397951091ed637)

のサンプルレポジトリです。

## 使い方

```bash
# 起動
sbt run

# テストを実施
sbt test

# カバレッジを測定してレポートを出力 (target/scala-2.13/scoverage-report/)
sbt clean coverage test coverageReport

# Docker イメージのビルド
sbt docker:publishLocal

# Dockerfile 生成のみ (target/docker/stage)
sbt docker:stage
```
