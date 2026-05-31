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
      title: "ACCUMULATING",
      value: accumulating,
      icon: "🟢",
      bg: "from-green-900 to-green-700"
    },

    {
      title: "WATCHING",
      value: watching,
      icon: "🟡",
      bg: "from-yellow-900 to-yellow-700"
    },

    {
      title: "REDUCING",
      value: reducing,
      icon: "🟠",
      bg: "from-orange-900 to-orange-700"
    },

    {
      title: "EXITED",
      value: exited,
      icon: "🔴",
      bg: "from-red-900 to-red-700"
    }
  ];

  return (

    <div className="grid md:grid-cols-4 gap-6">

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

            <div className="text-4xl mb-3">
              {card.icon}
            </div>

            <div className="text-4xl font-bold text-white">
              {card.value}
            </div>

            <div className="text-sm text-zinc-200 mt-2">
              {card.title}
            </div>

          </div>
        )
      )}

    </div>
  );
}