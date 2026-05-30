interface Props {
  value: string;
}

export default function StatusBadge(
  { value }: Props
) {

  let color =
    "bg-gray-600";

  if (
    value === "BUY" ||
    value === "RISK_ON"
  ) {
    color =
      "bg-green-600";
  }

  if (
    value === "HOLD"
  ) {
    color =
      "bg-yellow-600";
  }

  if (
    value === "AVOID" ||
    value === "RISK_OFF"
  ) {
    color =
      "bg-red-600";
  }

  return (
    <span
      className={`${color} px-3 py-1 rounded-full text-sm font-bold`}
    >
      {value}
    </span>
  );
}