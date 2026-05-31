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
        className="
          bg-slate-950
          min-h-screen
          text-white
          p-8
        "
      >

        Loading Portfolio...

      </main>

    );
  }

  const deploymentPct =
    Math.round(
      (
        data.investedCapital /
        data.capital
      ) * 100
    );

  const largestPosition =
    [...data.allocations]
      .sort(
        (a, b) =>
          b.allocationPct -
          a.allocationPct
      )[0];

  let marketOutlook =
    "⚪ Neutral";

  if (
    data.marketRegime ===
    "RISK_ON"
  ) {

    marketOutlook =
      "🟢 Favor Investing";
  }

  if (
    data.marketRegime ===
    "RECOVERY"
  ) {

    marketOutlook =
      "🟡 Accumulate Slowly";
  }

  if (
    data.marketRegime ===
    "RISK_OFF"
  ) {

    marketOutlook =
      "🟠 Be Selective";
  }

  if (
    data.marketRegime ===
    "PANIC"
  ) {

    marketOutlook =
      "🔴 Deploy Crash Cash";
  }

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
          mb-10
        "
      >
        Portfolio Allocator
      </h1>

      {/* DECISION CARDS */}

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
            Market Outlook
          </p>

          <p
            className="
              text-xl
              font-bold
              mt-2
            "
          >
            {marketOutlook}
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
            Deployment Status
          </p>

          <p
            className="
              text-2xl
              font-bold
              mt-2
            "
          >
            {deploymentPct}%
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
            Cash Ready For Dip
          </p>

          <p
            className="
              text-2xl
              font-bold
              mt-2
            "
          >
            ₹
            {data.cashReserve.toLocaleString()}
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
            Largest Position
          </p>

          <p
            className="
              text-xl
              font-bold
              mt-2
            "
          >
            {largestPosition.symbol}
          </p>

          <p
            className="
              text-green-400
              mt-1
            "
          >
            {largestPosition.allocationPct}%
          </p>

        </div>

      </div>

      {/* DEPLOYMENT BAR */}

      <div
        className="
          bg-slate-900
          rounded-xl
          p-6
          mb-8
        "
      >

        <div
          className="
            flex
            justify-between
            mb-3
          "
        >

          <span>
            Capital Deployment
          </span>

          <span>
            {deploymentPct}%
          </span>

        </div>

        <div
          className="
            w-full
            bg-slate-800
            rounded-full
            h-4
          "
        >

          <div
            className="
              bg-green-500
              h-4
              rounded-full
            "
            style={{
              width:
                `${deploymentPct}%`
            }}
          />

        </div>

      </div>

      {/* ALLOCATION TABLE */}

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
          Portfolio Allocations
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
                ETF
              </th>

              <th
                className="
                  text-left
                "
              >
                Score
              </th>

              <th
                className="
                  text-left
                "
              >
                Allocation %
              </th>

              <th
                className="
                  text-left
                "
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
                  className="
                    border-b
                    border-slate-800
                  "
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