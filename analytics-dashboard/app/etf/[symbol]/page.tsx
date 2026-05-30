"use client";

import { use } from "react";
import { useEffect, useState } from "react";

export default function EtfPage(
  {
    params
  }: {
    params: Promise<{
      symbol: string;
    }>;
  }
) {

  const { symbol } =
    use(params);

  const [probability, setProbability] =
    useState<any>(null);

  const [scorecard, setScorecard] =
    useState<any>(null);

  const [edge, setEdge] =
    useState<any>(null);

  useEffect(() => {

    Promise.all([

      fetch(
        `http://localhost:8080/api/probability/${symbol}`
      ).then(
        r => r.json()
      ),

      fetch(
        `http://localhost:8080/api/scorecard/${symbol}`
      ).then(
        r => r.json()
      ),

      fetch(
        `http://localhost:8080/api/edge/summary/${symbol}`
      ).then(
        r => r.json()
      )

    ])
      .then(
        ([p, s, e]) => {

          setProbability(p);
          setScorecard(s);
          setEdge(e);

        }
      )
      .catch(
        console.error
      );

  }, [symbol]);

  if (
    !probability ||
    !scorecard ||
    !edge
  ) {

    return (
      <main className="p-10 bg-slate-950 text-white min-h-screen">
        Loading ETF...
      </main>
    );
  }

  return (

    <main className="p-10 bg-slate-950 text-white min-h-screen">

      <h1 className="text-5xl font-bold mb-8">
        {symbol}
      </h1>

      <div className="grid md:grid-cols-4 gap-4 mb-8">

        <div className="bg-slate-900 p-6 rounded-xl">

          <p className="text-gray-400">
            Confidence
          </p>

          <p className="text-3xl font-bold">
            {probability.confidence}%
          </p>

        </div>

        <div className="bg-slate-900 p-6 rounded-xl">

          <p className="text-gray-400">
            Rating
          </p>

          <p className="text-3xl font-bold">
            {probability.rating}
          </p>

        </div>

        <div className="bg-slate-900 p-6 rounded-xl">

          <p className="text-gray-400">
            Bullish Signals
          </p>

          <p className="text-3xl font-bold">
            {probability.bullishSignals}
          </p>

        </div>

        <div className="bg-slate-900 p-6 rounded-xl">

          <p className="text-gray-400">
            Bearish Signals
          </p>

          <p className="text-3xl font-bold">
            {probability.bearishSignals}
          </p>

        </div>

      </div>

      <div className="grid md:grid-cols-2 gap-6">

        <div className="bg-slate-900 p-6 rounded-xl">

          <h2 className="text-2xl font-bold mb-4">
            Technical Scorecard
          </h2>

          <div className="space-y-2">

            <p>
              RSI: {scorecard.rsi.toFixed(2)}
            </p>

            <p>
              MACD Histogram:{" "}
              {scorecard.macdHistogram.toFixed(2)}
            </p>

            <p>
              SMA50: {scorecard.sma50.toFixed(2)}
            </p>

            <p>
              SMA200: {scorecard.sma200.toFixed(2)}
            </p>

            <p>
              Momentum:{" "}
              {scorecard.momentumScore.toFixed(2)}
            </p>

            <p>
              Trend Strength:{" "}
              {scorecard.trendStrength}
            </p>

            <p>
              Signal:{" "}
              {scorecard.signal}
            </p>

          </div>

        </div>

        <div className="bg-slate-900 p-6 rounded-xl">

          <h2 className="text-2xl font-bold mb-4">
            Edge Analysis
          </h2>

          <div className="space-y-2">

            <p>
              Trend Edge:{" "}
              {edge.trendWinRate.toFixed(2)}%
              {" "}
              ({edge.trendSampleSize} samples)
            </p>

            <p>
              MACD Edge:{" "}
              {edge.macdWinRate.toFixed(2)}%
              {" "}
              ({edge.macdSampleSize} samples)
            </p>

            <p>
              RSI Edge:{" "}
              {edge.rsiWinRate.toFixed(2)}%
              {" "}
              ({edge.rsiSampleSize} samples)
            </p>

            <p>
              Momentum Edge:{" "}
              {edge.momentumWinRate.toFixed(2)}%
              {" "}
              ({edge.momentumSampleSize} samples)
            </p>

            <p>
              Strongest Signal:{" "}
              {edge.strongestSignal}
            </p>

          </div>

        </div>

      </div>

    </main>

  );
}