type Props = {

  accumulating: number;

  watching: number;

  reducing: number;

  exited: number;
};

export default function RotationSummaryCards(
  {
    accumulating,
    watching,
    reducing,
    exited
  }: Props
) {

  const cards = [

    {
      title: "Buy More",
      subtitle: "Strong ETFs",
      value: accumulating,
      icon: "🟢",
      bg: "from-green-900 to-green-700"
    },

    {
      title: "Monitor",
      subtitle: "Watch Closely",
      value: watching,
      icon: "🟡",
      bg: "from-yellow-900 to-yellow-700"
    },

    {
      title: "Trim Position",
      subtitle: "Reduce Exposure",
      value: reducing,
      icon: "🟠",
      bg: "from-orange-900 to-orange-700"
    },

    {
      title: "Exit Position",
      subtitle: "Capital Protection",
      value: exited,
      icon: "🔴",
      bg: "from-red-900 to-red-700"
    }
  ];

  const total =
    accumulating +
    watching +
    reducing +
    exited;

  const healthScore =
    total === 0
      ? 0
      : Math.round(
          (
            (
              accumulating * 1.0 +
              watching * 0.5 +
              reducing * 0.2
            ) / total
          ) * 100
        );

  return (

    <div className="space-y-6">

      <div
        className="
          bg-slate-900
          rounded-xl
          p-6
          border
          border-slate-800
        "
      >

        <div
          className="
            flex
            justify-between
            items-center
          "
        >

          <div>

            <p
              className="
                text-sm
                text-slate-400
              "
            >
              Rotation Health
            </p>

            <p
              className="
                text-4xl
                font-bold
                mt-2
              "
            >
              {healthScore}%
            </p>

          </div>

          <div
            className="
              text-right
              text-slate-400
            "
          >

            <p>
              Portfolio Rotation Status
            </p>

            <p className="mt-1">
              {total} ETFs Tracked
            </p>

          </div>

        </div>

      </div>

      <div
        className="
          grid
          md:grid-cols-4
          gap-6
        "
      >

        {cards.map(
          card => (

            <div
              key={card.title}
              className={`
                bg-gradient-to-r
                ${card.bg}
                rounded-xl
                p-6
                shadow-lg
                hover:scale-105
                transition
              `}
            >

              <div
                className="
                  text-4xl
                  mb-3
                "
              >
                {card.icon}
              </div>

              <div
                className="
                  text-4xl
                  font-bold
                  text-white
                "
              >
                {card.value}
              </div>

              <div
                className="
                  text-lg
                  font-semibold
                  text-white
                  mt-2
                "
              >
                {card.title}
              </div>

              <div
                className="
                  text-sm
                  text-zinc-200
                  mt-1
                "
              >
                {card.subtitle}
              </div>

            </div>
          )
        )}

      </div>

    </div>
  );
}