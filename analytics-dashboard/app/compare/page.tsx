"use client";

import { useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function ComparePage() {

  const searchParams =
    useSearchParams();

  const left =
    searchParams.get("left") ??
    "MON100.NS";

  const right =
    searchParams.get("right") ??
    "GOLDBEES.NS";

  const [leftProbability, setLeftProbability] =
    useState<any>(null);

  const [rightProbability, setRightProbability] =
    useState<any>(null);

  const [leftScorecard, setLeftScorecard] =
    useState<any>(null);

  const [rightScorecard, setRightScorecard] =
    useState<any>(null);

  useEffect(() => {

    Promise.all([

      fetch(
        `http://localhost:8080/api/probability/${left}`
      ).then(
        r => r.json()
      ),

      fetch(
        `http://localhost:8080/api/probability/${right}`
      ).then(
        r => r.json()
      ),

      fetch(
        `http://localhost:8080/api/scorecard/${left}`
      ).then(
        r => r.json()
      ),

      fetch(
        `http://localhost:8080/api/scorecard/${right}`
      ).then(
        r => r.json()
      )

    ])
      .then(
        ([lp, rp, ls, rs]) => {

          setLeftProbability(lp);
          setRightProbability(rp);

          setLeftScorecard(ls);
          setRightScorecard(rs);

        }
      );

  }, [left, right]);

  if (
    !leftProbability ||
    !rightProbability ||
    !leftScorecard ||
    !rightScorecard
  ) {

    return (

      <main className="p-10 bg-slate-950 text-white min-h-screen">

        Loading Comparison...

      </main>

    );
  }

  const Row = (
    label: string,
    leftValue: any,
    rightValue: any
  ) => (

    <tr
      className="border-b border-slate-800"
    >

      <td className="py-3">
        {label}
      </td>

      <td>
        {leftValue}
      </td>

      <td>
        {rightValue}
      </td>

    </tr>
  );

  return (

    <main className="bg-slate-950 text-white min-h-screen p-8">

      <h1 className="text-5xl font-bold mb-8">

        ETF Comparison

      </h1>

      <div className="bg-slate-900 rounded-xl p-6">

        <table className="w-full">

          <thead>

            <tr
              className="border-b border-slate-700"
            >

              <th className="text-left py-3">

                Metric

              </th>

              <th className="text-left">

                {left}

              </th>

              <th className="text-left">

                {right}

              </th>

            </tr>

          </thead>

          <tbody>

            {Row(
              "Confidence",
              `${leftProbability.confidence}%`,
              `${rightProbability.confidence}%`
            )}

            {Row(
              "Rating",
              leftProbability.rating,
              rightProbability.rating
            )}

            {Row(
              "Price",
              leftScorecard.price.toFixed(2),
              rightScorecard.price.toFixed(2)
            )}

            {Row(
              "RSI",
              leftScorecard.rsi.toFixed(2),
              rightScorecard.rsi.toFixed(2)
            )}

            {Row(
              "Momentum",
              leftScorecard.momentumScore.toFixed(2),
              rightScorecard.momentumScore.toFixed(2)
            )}

            {Row(
              "SMA50",
              leftScorecard.sma50.toFixed(2),
              rightScorecard.sma50.toFixed(2)
            )}

            {Row(
              "SMA200",
              leftScorecard.sma200.toFixed(2),
              rightScorecard.sma200.toFixed(2)
            )}

            {Row(
              "MACD Histogram",
              leftScorecard.macdHistogram.toFixed(2),
              rightScorecard.macdHistogram.toFixed(2)
            )}

            {Row(
              "Trend Strength",
              leftScorecard.trendStrength,
              rightScorecard.trendStrength
            )}

            {Row(
              "Signal",
              leftScorecard.signal,
              rightScorecard.signal
            )}

          </tbody>

        </table>

      </div>

    </main>
  );
}