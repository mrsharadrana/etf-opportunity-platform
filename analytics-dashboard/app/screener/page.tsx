"use client";

import Link from "next/link";
import { useEffect, useState } from "react";

type ScreenerRow = {
  rank: number;
  symbol: string;
  totalScore: number;
  probabilityScore: number;
  relativeStrengthScore: number;
  rating: string;
};

export default function ScreenerPage() {

  const [rows, setRows] =
    useState<ScreenerRow[]>([]);

  const [loading, setLoading] =
    useState(true);

  useEffect(() => {

    fetch(
      "http://localhost:8080/api/screener"
    )
      .then(
        (res) => res.json()
      )
      .then(
        (data) => {

          setRows(
            data.rows
          );

          setLoading(
            false
          );
        }
      )
      .catch(
        console.error
      );

  }, []);

  if (loading) {

    return (

      <main
        className="
          bg-slate-950
          min-h-screen
          text-white
          p-8
        "
      >

        Loading Opportunities...

      </main>

    );
  }

  const topOpportunity =
    rows[0];

  const buyCandidates =
    rows.filter(
      row =>
        row.rating === "BUY" ||
        row.rating === "STRONG_BUY"
    ).length;

  const avoidCandidates =
    rows.filter(
      row =>
        row.rating === "AVOID"
    ).length;

  return (

    <main
      className="
        bg-slate-950
        min-h-screen
        text-white
        p-8
      "
    >

      <h1
        className="
          text-5xl
          font-bold
          mb-8
        "
      >
        ETF Opportunity Finder
      </h1>

      {/* TOP OPPORTUNITY */}

      <div
        className="
          bg-gradient-to-r
          from-green-900
          to-green-700
          rounded-xl
          p-8
          mb-8
        "
      >

        <div
          className="
            text-sm
            uppercase
            tracking-wider
            mb-2
          "
        >
          🏆 Top Opportunity
        </div>

        <h2
          className="
            text-4xl
            font-bold
            mb-3
          "
        >
          {topOpportunity.symbol}
        </h2>

        <div
          className="
            grid
            md:grid-cols-3
            gap-6
            mt-6
          "
        >

          <div>

            <p
              className="
                text-green-100
              "
            >
              Opportunity Score
            </p>

            <p
              className="
                text-3xl
                font-bold
              "
            >
              {topOpportunity.totalScore}
            </p>

          </div>

          <div>

            <p
              className="
                text-green-100
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
              {topOpportunity.probabilityScore}%
            </p>

          </div>

          <div>

            <p
              className="
                text-green-100
              "
            >
              Suggested Action
            </p>

            <p
              className="
                text-3xl
                font-bold
              "
            >
              🚀 Buy Now
            </p>

          </div>

        </div>

      </div>

      {/* SUMMARY CARDS */}

      <div
        className="
          grid
          md:grid-cols-3
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
            ETFs Screened
          </p>

          <p
            className="
              text-3xl
              font-bold
              mt-2
            "
          >
            {rows.length}
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
            Buy Candidates
          </p>

          <p
            className="
              text-3xl
              font-bold
              text-green-400
              mt-2
            "
          >
            {buyCandidates}
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
            Avoid Candidates
          </p>

          <p
            className="
              text-3xl
              font-bold
              text-red-400
              mt-2
            "
          >
            {avoidCandidates}
          </p>

        </div>

      </div>

      {/* TABLE */}

      <div
        className="
          bg-slate-900
          rounded-xl
          p-6
        "
      >

        <h2
          className="
            text-2xl
            font-bold
            mb-4
          "
        >
          Ranked Opportunities
        </h2>

        <table
          className="w-full"
        >

          <thead>

            <tr
              className="
                border-b
                border-slate-700
              "
            >

              <th
                className="
                  text-left
                  py-3
                "
              >
                Rank
              </th>

              <th
                className="
                  text-left
                "
              >
                ETF
              </th>

              <th
                className="
                  text-left
                "
              >
                Opportunity Score
              </th>

              <th
                className="
                  text-left
                "
              >
                Confidence
              </th>

              <th
                className="
                  text-left
                "
              >
                Momentum
              </th>

              <th
                className="
                  text-left
                "
              >
                Suggested Action
              </th>

            </tr>

          </thead>

          <tbody>

            {rows.map(
              (
                row
              ) => (

                <tr
                  key={
                    row.symbol
                  }
                  className="
                    border-b
                    border-slate-800
                  "
                >

                  <td
                    className="py-3"
                  >

                    {row.rank === 1
                      ? "🥇"
                      : row.rank === 2
                      ? "🥈"
                      : row.rank === 3
                      ? "🥉"
                      : row.rank}

                  </td>

                  <td>

                    <Link
                      href={`/etf/${row.symbol}`}
                      className="
                        text-cyan-400
                        hover:text-cyan-300
                      "
                    >

                      {row.symbol}

                    </Link>

                  </td>

                  <td>
                    {row.totalScore}
                  </td>

                  <td>
                    {row.probabilityScore}%
                  </td>

                  <td>
                    {row.relativeStrengthScore}
                  </td>

                  <td>

                    {row.rating === "STRONG_BUY"
                      ? "🚀 Buy Now"
                      : row.rating === "BUY"
                      ? "🟢 Buy"
                      : row.rating === "HOLD"
                      ? "🟡 Hold"
                      : "🔴 Avoid"}

                  </td>

                </tr>

              )
            )}

          </tbody>

        </table>

      </div>

    </main>

  );
}