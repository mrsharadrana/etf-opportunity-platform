interface Props {
  value: number;
}

export default function SignalStrength(
  { value }: Props
) {

  let color =
    "text-red-400";

  if (value >= 70) {
    color =
      "text-green-400";
  }
  else if (value >= 50) {
    color =
      "text-yellow-400";
  }

  return (
    <span
      className={`font-bold text-4xl ${color}`}
    >
      {value}%
    </span>
  );
}