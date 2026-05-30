"use client";

import Link from "next/link";
import { useEffect, useState } from "react";

import StatusBadge from "@/components/StatusBadge";
import KpiCard from "@/components/KpiCard";
import RegimeTimeline from "@/components/RegimeTimeline";
import PerformanceChart from "@/components/PerformanceChart";

export default function Home() {

  const [data, setData] =
    useState<any>(null);

  useEffect(() => {

    fetch(
      "http://localhost:8080/api/dashboard"
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
        className="p-10 text-white bg-slate-950 min-h-screen"
      >

        Loading ETF Dashboard...

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
        ETF Selector Platform
      </h1>

      {/* TOP SECTION */}

      <div
        className="grid md:grid-cols-4 gap-4 mb-8"
      >

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400 mb-2"
          >
            Market Regime
          </p>

          <StatusBadge
            value={
              data.selector.marketRegime
            }
          />

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Recommended ETF
          </p>

          <p
            className="text-2xl font-bold mt-2"
          >
            {
              data.selector
                .recommendedETF
            }
          </p>

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400"
          >
            Confidence
          </p>

          <p
            className="text-2xl font-bold mt-2"
          >
            {
              data.selector
                .confidence
            }
            %
          </p>

        </div>

        <div
          className="bg-slate-900 p-6 rounded-xl"
        >

          <p
            className="text-gray-400 mb-2"
          >
            Rating
          </p>

          <StatusBadge
            value={
              data.selector.rating
            }
          />

        </div>

      </div>

      {/* KPI CARDS */}

      <div
        className="grid md:grid-cols-5 gap-4 mb-8"
      >

        <KpiCard
          title="Strategy Return"
          value={`${data.performance.strategyReturn.toFixed(2)}%`}
        />

        <KpiCard
          title="CAGR"
          value={`${data.performance.cagr.toFixed(2)}%`}
        />

        <KpiCard
          title="Win Rate"
          value={`${data.performance.winRate.toFixed(2)}%`}
        />

        <KpiCard
          title="Max Drawdown"
          value={`${data.performance.maxDrawdown.toFixed(2)}%`}
        />

        <KpiCard
          title="Best ETF"
          value={data.performance.bestETF}
        />

      </div>

      {/* TIMELINE */}

      <div
        className="mb-8"
      >

        <RegimeTimeline
          history={data.history}
        />

      </div>

      {/* CHART */}

      <div
        className="mb-8"
      >

        <PerformanceChart
          performance={
            data.performance
          }
        />

      </div>

      {/* TOP RANKINGS */}

      <div
        className="bg-slate-900 rounded-xl p-6 mb-8"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Top ETF Rankings
        </h2>

        <table
          className="w-full"
        >

          <thead>

            <tr
              className="border-b border-slate-700"
            >

              <th
                className="text-left py-2"
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
                Momentum
              </th>

              <th
                className="text-left"
              >
                Signal
              </th>

            </tr>

          </thead>

          <tbody>

            {data.selector.topRankings.map(
              (
                etf: any
              ) => (

                <tr
                  key={
                    etf.symbol
                  }
                  className="border-b border-slate-800"
                >

                  <td
                    className="py-3"
                  >
                    {etf.rank === 1
                      ? "🥇"
                      : etf.rank === 2
                      ? "🥈"
                      : "🥉"}
                  </td>

                  <td>
                    <Link
                        href={`/etf/${etf.symbol}`}
                        className="text-cyan-400 hover:text-cyan-300"
                        >
                        {etf.symbol}
                        </Link>
                  </td>

                  <td>
                    {
                      etf.momentumScore
                    }
                  </td>

                  <td>

                    <StatusBadge
                      value={
                        etf.signal
                      }
                    />

                  </td>

                </tr>

              )
            )}

          </tbody>

        </table>

      </div>

      {/* HISTORY */}

      <div
        className="bg-slate-900 rounded-xl p-6"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Recommendation History
        </h2>

        <table
          className="w-full"
        >

          <thead>

            <tr
              className="border-b border-slate-700"
            >

              <th
                className="text-left py-2"
              >
                Date
              </th>

              <th
                className="text-left"
              >
                Regime
              </th>

              <th
                className="text-left"
              >
                ETF
              </th>

            </tr>

          </thead>

          <tbody>

            {data.history.map(
              (
                row: any
              ) => (

                <tr
                  key={
                    row.tradeDate
                  }
                  className="border-b border-slate-800"
                >

                  <td
                    className="py-2"
                  >
                    {
                      row.tradeDate
                    }
                  </td>

                  <td>

                    <StatusBadge
                      value={
                        row.marketRegime
                      }
                    />

                  </td>

                  <td>
                    {
                      row.recommendedETF
                    }
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