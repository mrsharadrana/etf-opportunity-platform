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
        className="bg-slate-950 min-h-screen text-white p-8"
      >

        Loading Screener...

      </main>

    );
  }

  return (

    <main
      className="bg-slate-950 min-h-screen text-white p-8"
    >

      <h1
        className="text-4xl font-bold mb-8"
      >
        ETF Screener
      </h1>

      <div
        className="bg-slate-900 rounded-xl p-6"
      >

        <table
          className="w-full"
        >

          <thead>

            <tr
              className="border-b border-slate-700"
            >

              <th
                className="text-left py-3"
              >
                Rank
              </th>

              <th
                className="text-left"
              >
                ETF
              </th>

              <th
                className="text-left"
              >
                Score
              </th>

              <th
                className="text-left"
              >
                Probability
              </th>

              <th
                className="text-left"
              >
                Relative Strength
              </th>

              <th
                className="text-left"
              >
                Rating
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
                  className="border-b border-slate-800"
                >

                  <td
                    className="py-3"
                  >

                    {row.rank}

                  </td>

                  <td>

                    <Link
                      href={`/etf/${row.symbol}`}
                      className="text-cyan-400 hover:text-cyan-300"
                    >

                      {row.symbol}

                    </Link>

                  </td>

                  <td>

                    {row.totalScore}

                  </td>

                  <td>

                    {row.probabilityScore}

                  </td>

                  <td>

                    {row.relativeStrengthScore}

                  </td>

                  <td>

                    {row.rating}

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