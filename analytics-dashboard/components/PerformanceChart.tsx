"use client";

import {
  BarChart,
  Bar,
  XAxis,
  Tooltip,
  ResponsiveContainer
} from "recharts";

interface Props {

  performance: any;
}

export default function PerformanceChart(
  {
    performance
  }: Props
) {

  const data = [

    {
      name: "Return",
      value:
        performance.strategyReturn
    },

    {
      name: "CAGR",
      value:
        performance.cagr
    },

    {
      name: "Win Rate",
      value:
        performance.winRate
    },

    {
      name: "Drawdown",
      value:
        performance.maxDrawdown
    }

  ];

  return (

    <div
      className="bg-slate-900 p-6 rounded-xl"
    >

      <h2 className="text-xl font-bold mb-4">
        Strategy Metrics
      </h2>

      <ResponsiveContainer
        width="100%"
        height={300}
      >

        <BarChart data={data}>

          <XAxis dataKey="name" />

          <Tooltip />

          <Bar dataKey="value" />

        </BarChart>

      </ResponsiveContainer>

    </div>
  );
}