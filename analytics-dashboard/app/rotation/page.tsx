"use client";

import { useEffect, useState } from "react";

import RotationPieChart
  from "@/components/RotationPieChart";

import RotationTable
  from "@/components/RotationTable";

import RotationSummaryCards
  from "@/components/RotationSummaryCards";

import SystemStatusBanner
  from "@/components/SystemStatusBanner";

type RotationDto = {
  symbol: string;
  signal: string;
  rotationState: string;
};

export default function RotationPage() {

  const [data, setData] =
    useState<RotationDto[]>([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  useEffect(() => {

    const loadData = async () => {

      try {

        setLoading(true);

        const response =
          await fetch(
            "http://localhost:8080/api/rotation"
          );

        if (!response.ok) {

          throw new Error(
            `HTTP ${response.status}`
          );
        }

        const json =
          await response.json();

        setData(json);

      } catch (err) {

        console.error(err);

        setError(
          "Unable to load rotation data."
        );

      } finally {

        setLoading(false);
      }
    };

    loadData();

  }, []);

  if (loading) {

    return (

      <main
        className="
          bg-slate-950
          min-h-screen
          text-white
          p-8
        "
      >

        <div className="text-xl">
          Loading Rotation Dashboard...
        </div>

      </main>
    );
  }

  if (error) {

    return (

      <main
        className="
          bg-slate-950
          min-h-screen
          text-white
          p-8
        "
      >

        <div
          className="
            bg-red-950
            border
            border-red-700
            rounded-lg
            p-4
          "
        >

          <h2 className="font-bold mb-2">
            Error
          </h2>

          <p>{error}</p>

        </div>

      </main>
    );
  }

  if (data.length === 0) {

    return (

      <main
        className="
          bg-slate-950
          min-h-screen
          text-white
          p-8
        "
      >

        <div
          className="
            bg-zinc-900
            rounded-lg
            p-6
          "
        >

          No rotation data available.

        </div>

      </main>
    );
  }

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

    <main
      className="
        bg-slate-950
        min-h-screen
        text-white
        p-8
        space-y-8
      "
    >

      <div>

        <h1
          className="
            text-4xl
            font-bold
          "
        >
          Rotation Command Center
        </h1>

        <p
          className="
            text-zinc-500
            mt-2
            mb-6
          "
        >
          ETF Lifecycle & Rotation Analytics
        </p>

      </div>

      <SystemStatusBanner />

      <RotationSummaryCards
        accumulating={counts.ACCUMULATING}
        watching={counts.WATCHING}
        reducing={counts.REDUCING}
        exited={counts.EXITED}
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