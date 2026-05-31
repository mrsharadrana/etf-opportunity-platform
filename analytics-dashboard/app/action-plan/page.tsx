"use client";

import { useEffect, useState } from "react";

type ActionPlan = {
  fearScore: number;
  fearState: string;
  crashLayer: string;
  deployPercent: number;
  deployAmount: number;
  buy: string[];
  reduce: string[];
  exit: string[];
};

export default function ActionPlanPage() {

  const [data, setData] =
    useState<ActionPlan | null>(
      null
    );

  useEffect(() => {

    fetch(
      "http://localhost:8080/api/action-plan"
    )
      .then(
        (res) => res.json()
      )
      .then(
        setData
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
          text-white
          min-h-screen
          p-8
        "
      >

        Loading Action Plan...

      </main>

    );
  }

  return (

    <main
      className="
        bg-slate-950
        text-white
        min-h-screen
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
        Weekly Action Plan
      </h1>

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

          <p className="text-gray-400">
            Fear & Greed
          </p>

          <p
            className="
              text-2xl
              font-bold
              mt-2
            "
          >
            {data.fearState}
          </p>

          <p>
            Score: {data.fearScore}
          </p>

        </div>

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <p className="text-gray-400">
            Crash Layer
          </p>

          <p
            className="
              text-2xl
              font-bold
              mt-2
            "
          >
            {data.crashLayer}
          </p>

        </div>

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <p className="text-gray-400">
            Deploy %
          </p>

          <p
            className="
              text-2xl
              font-bold
              mt-2
            "
          >
            {data.deployPercent}%
          </p>

        </div>

        <div
          className="
            bg-slate-900
            p-6
            rounded-xl
          "
        >

          <p className="text-gray-400">
            Deploy Amount
          </p>

          <p
            className="
              text-2xl
              font-bold
              mt-2
            "
          >
            ₹{data.deployAmount.toLocaleString()}
          </p>

        </div>

      </div>

      <div
        className="
          grid
          md:grid-cols-3
          gap-6
        "
      >

        <div
          className="
            bg-green-950
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
            🚀 BUY
          </h2>

          {data.buy.length === 0
            ? <p>No Buy Actions</p>
            : data.buy.map(
                item => (
                  <p
                    key={item}
                    className="mb-2"
                  >
                    {item}
                  </p>
                )
              )}

        </div>

        <div
          className="
            bg-orange-950
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
            🟠 REDUCE
          </h2>

          {data.reduce.length === 0
            ? <p>No Reduce Actions</p>
            : data.reduce.map(
                item => (
                  <p
                    key={item}
                    className="mb-2"
                  >
                    {item}
                  </p>
                )
              )}

        </div>

        <div
          className="
            bg-red-950
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
            🔴 EXIT
          </h2>

          {data.exit.length === 0
            ? <p>No Exit Actions</p>
            : data.exit.map(
                item => (
                  <p
                    key={item}
                    className="mb-2"
                  >
                    {item}
                  </p>
                )
              )}

        </div>

      </div>

    </main>

  );
}