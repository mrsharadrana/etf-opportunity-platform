interface Props {

  title: string;

  value: string;
}

export default function KpiCard(
  {
    title,
    value
  }: Props
) {

  return (

    <div className="bg-slate-900 rounded-xl p-6">

      <p className="text-gray-400">
        {title}
      </p>

      <p className="text-3xl font-bold mt-2">
        {value}
      </p>

    </div>
  );
}