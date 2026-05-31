"use client";

import { useEffect, useState } from "react";

type BacktestResult = {
  recommendationReturn: number;
  allocatorReturn: number;
  winner: string;
};

export default function PortfolioBacktestPage() {

  const [data, setData] =
    useState<BacktestResult | null>(
      null
    );

  useEffect(() => {

    fetch(
      "http://localhost:8080/api/portfolio/backtest"
    )
      .then(
        (res) => res.json()
      )
      .then(
        (json) => setData(json)
      )
      .catch(
        console.error
      );

  }, []);

  if (!data) {

    return (

      <main
        className="bg-slate-950 min-h-screen text-white p-8"
      >

        Loading Portfolio Backtest...

      </main>

    );
  }

  return (

    <main
      className="bg-slate-950 min-h-screen text-white p-8"
    >

      <h1
        className="text-5xl font-bold mb-10"
      >
        Portfolio Backtest
      </h1>

      <div
        className="grid md:grid-cols-3 gap-4 mb-8"
      >

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Recommendation Return
          </p>

          <p
            className="text-3xl font-bold mt-2"
          >
            {data.recommendationReturn.toFixed(2)}%
          </p>

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Allocator Return
          </p>

          <p
            className="text-3xl font-bold mt-2"
          >
            {data.allocatorReturn.toFixed(2)}%
          </p>

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Winner
          </p>

          <p
            className="text-3xl font-bold mt-2"
          >
            {data.winner}
          </p>

        </div>

      </div>

      <div
        className="bg-slate-900 rounded-xl p-6"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Performance Comparison
        </h2>

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
                Strategy
              </th>

              <th
                className="text-left"
              >
                Return
              </th>

            </tr>

          </thead>

          <tbody>

            <tr
              className="border-b border-slate-800"
            >

              <td
                className="py-3"
              >
                Recommendation Engine
              </td>

              <td>
                {data.recommendationReturn.toFixed(2)}%
              </td>

            </tr>

            <tr>

              <td
                className="py-3"
              >
                Portfolio Allocator
              </td>

              <td>
                {data.allocatorReturn.toFixed(2)}%
              </td>

            </tr>

          </tbody>

        </table>

      </div>

    </main>

  );
}