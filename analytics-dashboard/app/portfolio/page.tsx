"use client";

import { useEffect, useState } from "react";

type Allocation = {
  symbol: string;
  score: number;
  allocationPct: number;
  allocationAmount: number;
};

type PortfolioResponse = {
  capital: number;
  investedCapital: number;
  cashReserve: number;
  marketRegime: string;
  totalScore: number;
  allocations: Allocation[];
};

export default function PortfolioPage() {

  const [data, setData] =
    useState<PortfolioResponse | null>(
      null
    );

  useEffect(() => {

    fetch(
      "http://localhost:8080/api/portfolio"
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

        Loading Portfolio...

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
        Portfolio Allocator
      </h1>

      {/* TOP CARDS */}

      <div
        className="grid md:grid-cols-4 gap-4 mb-8"
      >

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Market Regime
          </p>

          <p
            className="text-2xl font-bold mt-2"
          >
            {data.marketRegime}
          </p>

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Capital
          </p>

          <p
            className="text-2xl font-bold mt-2"
          >
            ₹{data.capital.toLocaleString()}
          </p>

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Invested Capital
          </p>

          <p
            className="text-2xl font-bold mt-2"
          >
            ₹{data.investedCapital.toLocaleString()}
          </p>

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Cash Reserve
          </p>

          <p
            className="text-2xl font-bold mt-2"
          >
            ₹{data.cashReserve.toLocaleString()}
          </p>

        </div>

      </div>

      {/* ALLOCATIONS */}

      <div
        className="bg-slate-900 rounded-xl p-6"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Portfolio Allocations
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
                Allocation %
              </th>

              <th
                className="text-left"
              >
                Amount
              </th>

            </tr>

          </thead>

          <tbody>

            {data.allocations.map(
              (
                allocation
              ) => (

                <tr
                  key={
                    allocation.symbol
                  }
                  className="border-b border-slate-800"
                >

                  <td
                    className="py-3"
                  >
                    {allocation.symbol}
                  </td>

                  <td>
                    {allocation.score}
                  </td>

                  <td>
                    {allocation.allocationPct}%
                  </td>

                  <td>
                    ₹
                    {allocation.allocationAmount.toLocaleString()}
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