"use client";

import { useEffect, useState } from "react";

type RegimeResponse = {
  tradeDate: string;
  marketRegime: string;
  topRankedEtf: string;
};

type FearFactor = {
  factorName: string;
  rawValue: number;
  normalizedScore: number;
  weight: number;
  contribution: number;
};

type FearResponse = {
  fearScore: number;
  fearState: string;
  factors: FearFactor[];
};

type MarketStateResponse = {
  india: string;
  usa: string;
  china: string;
  gold: string;
  silver: string;
};

export default function MarketPage() {

  const [regime, setRegime] =
    useState<RegimeResponse | null>(
      null
    );

  const [fear, setFear] =
    useState<FearResponse | null>(
      null
    );

  const [marketState, setMarketState] =
    useState<MarketStateResponse | null>(
      null
    );

  useEffect(() => {

    Promise.all([

       fetch(
        "http://localhost:8080/api/market-regime"
        ).then(
        res => res.json()
      ),

      fetch(
        "http://localhost:8080/api/fear/v2"
      ).then(
        res => res.json()
      ),

      fetch(
        "http://localhost:8080/api/market-state"
      ).then(
        res => res.json()
      )

    ])
      .then(
        ([regimeData, fearData, marketData]) => {

          setRegime(
            regimeData
          );

          setFear(
            fearData
          );

          setMarketState(
            marketData
          );

        }
      )
      .catch(
        console.error
      );

  }, []);

  if (
    !regime ||
    !fear ||
    !marketState
  ) {

    return (

      <main
        className="bg-slate-950 min-h-screen text-white p-8"
      >

        Loading Market Intelligence...

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
        Market Intelligence
      </h1>

      <div
        className="grid md:grid-cols-3 gap-4 mb-8"
      >

        <div
          className="bg-slate-900 rounded-xl p-6"
        >

          <p
            className="text-gray-400"
          >
            Market Regime
          </p>

          <p
            className="text-3xl font-bold mt-2"
          >
            {regime.marketRegime}
          </p>

        </div>

        <div
          className="bg-slate-900 rounded-xl p-6"
        >

          <p
            className="text-gray-400"
          >
            Fear Score
          </p>

          <p
            className="text-3xl font-bold mt-2"
          >
            {fear.fearScore}
          </p>

          <p
            className="text-cyan-400 mt-2"
          >
            {fear.fearState}
          </p>

        </div>

        <div
          className="bg-slate-900 rounded-xl p-6"
        >

          <p
            className="text-gray-400"
          >
            India Market State
          </p>

          <p
            className="text-3xl font-bold mt-2"
          >
            {marketState.india}
          </p>

        </div>

      </div>

      <div
        className="bg-slate-900 rounded-xl p-6 mb-8"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Regime Details
        </h2>

        <table
          className="w-full"
        >

          <tbody>

            <tr>

              <td
                className="py-2"
              >
                Trade Date
              </td>

              <td>
                {regime.tradeDate}
              </td>

            </tr>

            <tr>

              <td
                className="py-2"
              >
                Top Ranked ETF
              </td>

              <td>
                {regime.topRankedEtf}
              </td>

            </tr>

          </tbody>

        </table>

      </div>

      <div
        className="bg-slate-900 rounded-xl p-6 mb-8"
      >

        <h2
          className="text-2xl font-bold mb-6"
        >
          Fear & Greed Breakdown
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
                Factor
              </th>

              <th
                className="text-left"
              >
                Raw Value
              </th>

              <th
                className="text-left"
              >
                Score
              </th>

              <th
                className="text-left"
              >
                Weight
              </th>

              <th
                className="text-left"
              >
                Contribution
              </th>

            </tr>

          </thead>

          <tbody>

            {fear.factors.map(
              (
                factor
              ) => (

                <tr
                  key={
                    factor.factorName
                  }
                  className="border-b border-slate-800"
                >

                  <td
                    className="py-3"
                  >
                    {factor.factorName}
                  </td>

                  <td>
                    {factor.rawValue.toFixed(2)}
                  </td>

                  <td>
                    {factor.normalizedScore.toFixed(2)}
                  </td>

                  <td>
                    {factor.weight}%
                  </td>

                  <td>
                    {factor.contribution.toFixed(2)}
                  </td>

                </tr>

              )
            )}

          </tbody>

        </table>

      </div>

      <div
        className="bg-slate-900 rounded-xl p-6"
      >

        <h2
          className="text-2xl font-bold mb-4"
        >
          Global Market States
        </h2>

        <table
          className="w-full"
        >

          <tbody>

            <tr>

              <td
                className="py-2"
              >
                India
              </td>

              <td>
                {marketState.india}
              </td>

            </tr>

            <tr>

              <td
                className="py-2"
              >
                USA
              </td>

              <td>
                {marketState.usa}
              </td>

            </tr>

            <tr>

              <td
                className="py-2"
              >
                China
              </td>

              <td>
                {marketState.china}
              </td>

            </tr>

            <tr>

              <td
                className="py-2"
              >
                Gold
              </td>

              <td>
                {marketState.gold}
              </td>

            </tr>

            <tr>

              <td
                className="py-2"
              >
                Silver
              </td>

              <td>
                {marketState.silver}
              </td>

            </tr>

          </tbody>

        </table>

      </div>

    </main>

  );
}