"use client";

import { use } from "react";
import { useEffect, useState } from "react";

import SignalStrength
  from "@/components/SignalStrength";

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

      <main
        className="
          p-10
          bg-slate-950
          text-white
          min-h-screen
        "
      >

        Loading ETF...

      </main>
    );
  }

  let action =
    "Monitor";

  if (
    probability.rating ===
    "BUY"
  ) {

    action =
      "🚀 Buy Now";
  }

  if (
    probability.rating ===
    "HOLD"
  ) {

    action =
      "🟡 Hold";
  }

  if (
    probability.rating ===
    "AVOID"
  ) {

    action =
      "🔴 Avoid";
  }

  return (

    <main
      className="
        p-10
        bg-slate-950
        text-white
        min-h-screen
      "
    >

      <h1
        className="
          text-5xl
          font-bold
          mb-2
        "
      >
        {symbol}
      </h1>

      <p
        className="
          text-2xl
          font-semibold
          text-cyan-400
          mb-8
        "
      >
        {action}
      </p>

      {/* DECISION SUMMARY */}

      <div
        className="
          grid
          md:grid-cols-4
          gap-4
          mb-8
        "
      >

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <p
            className="
              text-gray-400
            "
          >
            Confidence
          </p>

          <p
            className="
              text-3xl
              font-bold
            "
          >
            {probability.confidence}%
          </p>

        </div>

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <p
            className="
              text-gray-400
            "
          >
            Rating
          </p>

          <p
            className="
              text-3xl
              font-bold
            "
          >
            {probability.rating}
          </p>

        </div>

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <p
            className="
              text-gray-400
            "
          >
            Bullish Signals
          </p>

          <p
            className="
              text-3xl
              font-bold
              text-green-400
            "
          >
            {probability.bullishSignals}
          </p>

        </div>

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <p
            className="
              text-gray-400
            "
          >
            Bearish Signals
          </p>

          <p
            className="
              text-3xl
              font-bold
              text-red-400
            "
          >
            {probability.bearishSignals}
          </p>

        </div>

      </div>

      {/* SIGNAL STRENGTH */}

      <div
        className="
          bg-slate-900
          p-6
          rounded-xl
          mb-8
        "
      >

        <SignalStrength
          value={
            probability.confidence
          }
        />

      </div>

      {/* WHY THIS ETF */}

      <div
        className="
          bg-slate-900
          p-6
          rounded-xl
          mb-8
        "
      >

        <h2
          className="
            text-2xl
            font-bold
            mb-4
          "
        >
          Why This ETF?
        </h2>

        <div
          className="
            space-y-2
          "
        >

          <p>
            ✅ Trend:
            {" "}
            {scorecard.trendStrength}
          </p>

          <p>
            ✅ Momentum:
            {" "}
            {scorecard.momentumScore.toFixed(2)}
          </p>

          <p>
            ✅ Strongest Signal:
            {" "}
            {edge.strongestSignal}
          </p>

          <p>
            ✅ Above 200 DMA:
            {" "}
            {scorecard.above200DMA
              ? "Yes"
              : "No"}
          </p>

        </div>

      </div>

      <div
        className="
          grid
          md:grid-cols-2
          gap-6
        "
      >

        {/* TECHNICALS */}

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <h2
            className="
              text-2xl
              font-bold
              mb-4
            "
          >
            Technical Scorecard
          </h2>

          <div
            className="
              space-y-2
            "
          >

            <p>
              Current Price:
              {" "}
              ₹
              {scorecard.price.toFixed(2)}
            </p>

            <p>
              RSI:
              {" "}
              {scorecard.rsi.toFixed(2)}
            </p>

            <p>
              SMA50:
              {" "}
              {scorecard.sma50.toFixed(2)}
            </p>

            <p>
              SMA200:
              {" "}
              {scorecard.sma200.toFixed(2)}
            </p>

            <p>
              MACD Histogram:
              {" "}
              {scorecard.macdHistogram.toFixed(2)}
            </p>

            <p>
              Signal:
              {" "}
              {scorecard.signal}
            </p>

          </div>

        </div>

        {/* EDGE ANALYSIS */}

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <h2
            className="
              text-2xl
              font-bold
              mb-4
            "
          >
            Historical Edge
          </h2>

          <div
            className="
              space-y-2
            "
          >

            <p>
              Trend Win Rate:
              {" "}
              {edge.trendWinRate.toFixed(1)}%
            </p>

            <p>
              MACD Win Rate:
              {" "}
              {edge.macdWinRate.toFixed(1)}%
            </p>

            <p>
              RSI Win Rate:
              {" "}
              {edge.rsiWinRate.toFixed(1)}%
            </p>

            <p>
              Momentum Win Rate:
              {" "}
              {edge.momentumWinRate.toFixed(1)}%
            </p>

          </div>

        </div>

      </div>

    </main>

  );
}