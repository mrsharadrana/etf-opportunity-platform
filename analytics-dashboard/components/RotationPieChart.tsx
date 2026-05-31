"use client";

import {
  PieChart,
  Pie,
  Tooltip,
  ResponsiveContainer,
  Cell,
  Legend
} from "recharts";

type Props = {
  data: {
    state: string;
    count: number;
  }[];
};

const COLORS = [
  "#22c55e",
  "#eab308",
  "#f97316",
  "#ef4444"
];

export default function RotationPieChart(
  {
    data
  }: Props
) {

  return (
    <div className="bg-zinc-900 rounded-xl p-4 border border-zinc-800">

    <h2 className="text-xl font-bold mb-4 text-white">
        Rotation Overview
      </h2>

      <div className="h-[350px]">

        <ResponsiveContainer>

          <PieChart>

            <Pie
              data={data}
              dataKey="count"
              nameKey="state"
              outerRadius={120}
              label
            >
              {data.map(
                (
                  _,
                  index
                ) => (
                  <Cell
                    key={index}
                    fill={
                      COLORS[
                        index %
                        COLORS.length
                      ]
                    }
                  />
                )
              )}
            </Pie>

            <Tooltip />

            <Legend />

          </PieChart>

        </ResponsiveContainer>

      </div>

    </div>
  );
}