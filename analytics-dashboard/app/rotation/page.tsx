"use client";

import { useEffect, useState } from "react";

import RotationPieChart
  from "@/components/RotationPieChart";

import RotationTable
  from "@/components/RotationTable";

import RotationSummaryCards
  from "@/components/RotationSummaryCards";

type RotationDto = {

  symbol: string;

  signal: string;

  rotationState: string;
};

export default function RotationPage() {

  const [
    data,
    setData
  ] = useState<
    RotationDto[]
  >([]);

  useEffect(
    () => {

      fetch(
        "http://localhost:8080/api/rotation"
      )
        .then(
          r => r.json()
        )
        .then(
          setData
        );

    },
    []
  );

  const counts = {

    ACCUMULATING: 0,

    WATCHING: 0,

    REDUCING: 0,

    EXITED: 0
  };

  data.forEach(
    item => {

      counts[
        item.rotationState as keyof typeof counts
      ]++;
    }
  );

  const chartData = [

    {
      state: "ACCUMULATING",
      count: counts.ACCUMULATING
    },

    {
      state: "WATCHING",
      count: counts.WATCHING
    },

    {
      state: "REDUCING",
      count: counts.REDUCING
    },

    {
      state: "EXITED",
      count: counts.EXITED
    }
  ];

  return (

    <main className="p-8 space-y-8">

      <div>

        <h1 className="text-4xl font-bold">
          Rotation Command Center
        </h1>

        <p className="text-zinc-500 mt-2">
          ETF Lifecycle & Rotation Analytics
        </p>

      </div>

      <RotationSummaryCards
        accumulating={
          counts.ACCUMULATING
        }
        watching={
          counts.WATCHING
        }
        reducing={
          counts.REDUCING
        }
        exited={
          counts.EXITED
        }
      />

      <RotationPieChart
        data={chartData}
      />

      <RotationTable
        rows={data}
      />

    </main>
  );
}