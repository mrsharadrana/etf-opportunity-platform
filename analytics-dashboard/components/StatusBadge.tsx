interface Props {
  value: string;
}

export default function StatusBadge(
  { value }: Props
) {

  let color =
    "bg-gray-600";

  let displayValue =
    value;

  switch (value) {

    case "RISK_ON":

      color =
        "bg-green-600";

      displayValue =
        "🟢 Favor Investing";

      break;

    case "RECOVERY":

      color =
        "bg-yellow-600";

      displayValue =
        "🟡 Accumulate Slowly";

      break;

    case "RISK_OFF":

      color =
        "bg-orange-600";

      displayValue =
        "🟠 Be Selective";

      break;

    case "PANIC":

      color =
        "bg-red-700";

      displayValue =
        "🔴 Deploy Crash Cash";

      break;

    case "BUY":

      color =
        "bg-green-600";

      displayValue =
        "🟢 Buy";

      break;

    case "STRONG_BUY":

      color =
        "bg-green-700";

      displayValue =
        "🚀 Strong Buy";

      break;

    case "HOLD":

      color =
        "bg-yellow-600";

      displayValue =
        "🟡 Hold";

      break;

    case "REDUCE_20":

      color =
        "bg-orange-600";

      displayValue =
        "🟠 Trim 20%";

      break;

    case "REDUCE_40":

      color =
        "bg-orange-700";

      displayValue =
        "🟠 Trim 40%";

      break;

    case "EXIT":

      color =
        "bg-red-700";

      displayValue =
        "🔴 Exit";

      break;

    case "AVOID":

      color =
        "bg-red-600";

      displayValue =
        "🔴 Avoid";

      break;
  }

  return (

    <span
      className={`
        ${color}
        px-3
        py-1
        rounded-full
        text-sm
        font-bold
      `}
    >

      {displayValue}

    </span>

  );
}