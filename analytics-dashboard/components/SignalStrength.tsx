interface Props {
  value: number;
}

export default function SignalStrength(
  { value }: Props
) {

  let color =
    "bg-red-500";

  if (value >= 70) {

    color =
      "bg-green-500";
  }
  else if (value >= 50) {

    color =
      "bg-yellow-500";
  }

  return (

    <div>

      <div
        className="
          flex
          justify-between
          mb-2
        "
      >

        <span>
          Signal Strength
        </span>

        <span
          className="
            font-bold
          "
        >
          {value}%
        </span>

      </div>

      <div
        className="
          w-full
          bg-slate-700
          rounded-full
          h-4
        "
      >

        <div
          className={`
            ${color}
            h-4
            rounded-full
          `}
          style={{
            width: `${value}%`
          }}
        />

      </div>

    </div>

  );
}