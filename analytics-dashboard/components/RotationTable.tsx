type RotationRow = {

  symbol: string;

  signal: string;

  rotationState: string;
};

export default function RotationTable(
  {
    rows
  }: {
    rows: RotationRow[];
  }
) {

  const stateIcon = (
    state: string
  ) => {

    switch (
      state
    ) {

      case "ACCUMULATING":
        return "🟢";

      case "WATCHING":
        return "🟡";

      case "REDUCING":
        return "🟠";

      default:
        return "🔴";
    }
  };

  return (

    <div className="bg-zinc-900 rounded-xl p-6 border border-zinc-800 text-white">

      <h2 className="text-2xl font-bold mb-6">
        ETF Rotation Map
      </h2>

      <table className="w-full">

        <thead>

          <tr className="border-b border-zinc-700">

            <th className="text-left py-3">
              ETF
            </th>

            <th className="text-left py-3">
              Signal
            </th>

            <th className="text-left py-3">
              State
            </th>

          </tr>

        </thead>

        <tbody>

          {rows.map(
            (
              row
            ) => (

              <tr
                key={row.symbol}
                className="
                  border-b
                  border-zinc-800
                  hover:bg-zinc-800
                  transition
                "
              >

                <td className="py-4 font-medium">
                  {row.symbol}
                </td>

                <td className="py-4">
                  {row.signal}
                </td>

                <td className="py-4">

                  {
                    stateIcon(
                      row.rotationState
                    )
                  }{" "}

                  {row.rotationState}

                </td>

              </tr>

            )
          )}

        </tbody>

      </table>

    </div>
  );
}