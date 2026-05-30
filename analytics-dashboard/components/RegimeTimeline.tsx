interface Props {

  history: any[];
}

export default function RegimeTimeline(
  {
    history
  }: Props
) {

  return (

    <div
      className="bg-slate-900 p-6 rounded-xl"
    >

      <h2 className="text-xl font-bold mb-4">
        Regime Timeline
      </h2>

      <div className="flex gap-1">

        {history
          .slice(0, 30)
          .map((item) => (

            <div
              key={item.tradeDate}
              title={item.tradeDate}
              className={
                item.marketRegime ===
                "RISK_ON"
                  ? "w-6 h-6 bg-green-500 rounded"
                  : "w-6 h-6 bg-red-500 rounded"
              }
            />

          ))}

      </div>

    </div>
  );
}