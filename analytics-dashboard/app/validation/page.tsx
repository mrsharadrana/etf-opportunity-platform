"use client";

import { useEffect, useState } from "react";

type ValidationResponse = {
  symbol: string;
  validation: {
    signalCount: number;
    winRatePct: number;
    averageReturnPct: number;
    bestReturnPct: number;
    worstReturnPct: number;
    averageHoldingDays: number;
    confidence: string;
  };
  backtest: {
    totalReturnPct: number;
    cagr: number;
    tradesExecuted: number;
    winningTrades: number;
    losingTrades: number;
    winRatePct: number;
    maxDrawdownPct: number;
    averageTradeReturnPct: number;
    bestTradeReturnPct: number;
    worstTradeReturnPct: number;
    averageHoldingDays: number;
    sharpeRatio: number;
    sortinoRatio: number;
  };
};

export default function ValidationPage() {

  const [data, setData] =
    useState<ValidationResponse | null>(
      null
    );

  useEffect(() => {

    fetch(
      "http://localhost:8080/api/validation/MON100.NS"
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

        Loading Validation Dashboard...

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
        Validation Dashboard
      </h1>

      <div
        className="grid md:grid-cols-4 gap-4 mb-8"
      >

        <div className="bg-slate-900 p-6 rounded-xl">
          <p className="text-gray-400">Win Rate</p>
          <p className="text-3xl font-bold mt-2">
            {data.validation.winRatePct.toFixed(2)}%
          </p>
        </div>

        <div className="bg-slate-900 p-6 rounded-xl">
          <p className="text-gray-400">Signal Count</p>
          <p className="text-3xl font-bold mt-2">
            {data.validation.signalCount}
          </p>
        </div>

        <div className="bg-slate-900 p-6 rounded-xl">
          <p className="text-gray-400">Confidence</p>
          <p className="text-3xl font-bold mt-2">
            {data.validation.confidence}
          </p>
        </div>

        <div className="bg-slate-900 p-6 rounded-xl">
          <p className="text-gray-400">Avg Return</p>
          <p className="text-3xl font-bold mt-2">
            {data.validation.averageReturnPct.toFixed(2)}%
          </p>
        </div>

      </div>

      <div
        className="bg-slate-900 rounded-xl p-6 mb-8"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Validation Metrics
        </h2>

        <table className="w-full">

          <tbody>

            <tr>
              <td className="py-2">Best Return</td>
              <td>{data.validation.bestReturnPct.toFixed(2)}%</td>
            </tr>

            <tr>
              <td className="py-2">Worst Return</td>
              <td>{data.validation.worstReturnPct.toFixed(2)}%</td>
            </tr>

            <tr>
              <td className="py-2">Average Holding Days</td>
              <td>{data.validation.averageHoldingDays.toFixed(2)}</td>
            </tr>

          </tbody>

        </table>

      </div>

      <div
        className="bg-slate-900 rounded-xl p-6"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Backtest Metrics
        </h2>

        <table className="w-full">

          <tbody>

            <tr>
              <td className="py-2">Total Return</td>
              <td>{data.backtest.totalReturnPct.toFixed(2)}%</td>
            </tr>

            <tr>
              <td className="py-2">CAGR</td>
              <td>{data.backtest.cagr.toFixed(2)}%</td>
            </tr>

            <tr>
              <td className="py-2">Sharpe Ratio</td>
              <td>{data.backtest.sharpeRatio.toFixed(2)}</td>
            </tr>

            <tr>
              <td className="py-2">Sortino Ratio</td>
              <td>{data.backtest.sortinoRatio.toFixed(2)}</td>
            </tr>

            <tr>
              <td className="py-2">Max Drawdown</td>
              <td>{data.backtest.maxDrawdownPct.toFixed(2)}%</td>
            </tr>

            <tr>
              <td className="py-2">Trades Executed</td>
              <td>{data.backtest.tradesExecuted}</td>
            </tr>

          </tbody>

        </table>

      </div>

    </main>

  );
}