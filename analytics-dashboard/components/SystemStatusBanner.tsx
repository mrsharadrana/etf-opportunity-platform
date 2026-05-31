"use client";

import { useEffect, useState } from "react";

type StatusDto = {
  generatedAt: string;
  marketStatus: string;
};

export default function SystemStatusBanner() {

  const [data, setData] =
    useState<StatusDto | null>(null);

  useEffect(() => {

    fetch(
      "http://localhost:8080/api/system-status"
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
    return null;
  }

  const generatedAt =
    new Date(
      data.generatedAt
    ).toLocaleString(
      "en-IN",
      {
        dateStyle: "medium",
        timeStyle: "short"
      }
    );

  return (

    <div
      className="
        bg-slate-900
        border
        border-slate-800
        rounded-xl
        p-5
        mb-8
        shadow-sm
      "
    >

      <div
        className="
          flex
          justify-between
          items-center
          flex-wrap
          gap-6
        "
      >

        <div>

          <p
            className="
              text-slate-400
              text-sm
              uppercase
              tracking-wide
            "
          >
            Last Analysis Run
          </p>

          <p
            className="
              font-bold
              text-lg
              text-white
            "
          >
            🕒 {generatedAt}
          </p>

        </div>

        <div>

          <p
            className="
              text-slate-400
              text-sm
              uppercase
              tracking-wide
            "
          >
            Market Status
          </p>

          <div
            className={`
              inline-flex
              items-center
              gap-2
              px-4
              py-2
              rounded-full
              font-bold
              animate-pulse
              ${
                data.marketStatus === "OPEN"
                  ? "bg-green-900 text-green-300"
                  : "bg-orange-900 text-orange-300"
              }
            `}
          >

            <span>
              {data.marketStatus === "OPEN"
                ? "🟢"
                : "🟠"}
            </span>

            <span>
              {data.marketStatus}
            </span>

          </div>

        </div>

      </div>

    </div>
  );
}