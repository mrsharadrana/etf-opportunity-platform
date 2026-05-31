"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

const menuItems = [
  {
    label: "Dashboard",
    href: "/",
    icon: "🏠",
  },
  {
    label: "Market",
    href: "/market",
    icon: "📈",
  },
  {
    label: "Screener",
    href: "/screener",
    icon: "🔍",
  },
  {
    label: "Portfolio",
    href: "/portfolio",
    icon: "💼",
  },
  {
    label: "Backtest",
    href: "/portfolio/backtest",
    icon: "🧪",
  },
  {
    label: "Rotation",
    href: "/rotation",
    icon: "🔄",
  },
  {
    label: "Compare",
    href: "/compare",
    icon: "⚖️",
  },
  {
    label: "Validation",
    href: "/validation",
    icon: "✅",
  },
];

export default function Sidebar() {
  const pathname = usePathname();

  return (
    <aside
      className="
        w-64
        bg-slate-900
        border-r
        border-slate-800
        text-white
        min-h-screen
        p-6
      "
    >
      <div className="mb-8">
        <h1 className="text-xl font-bold">
          ETF Selector
        </h1>

        <p className="text-sm text-slate-400 mt-1">
          Decision Engine
        </p>
      </div>

      <nav className="space-y-2">
        {menuItems.map((item) => {
          const active =
            pathname === item.href;

          return (
            <Link
              key={item.href}
              href={item.href}
              className={`
                flex
                items-center
                gap-3
                px-4
                py-3
                rounded-lg
                transition
                ${
                  active
                    ? "bg-cyan-600 text-white"
                    : "hover:bg-slate-800 text-slate-300"
                }
              `}
            >
              <span>
                {item.icon}
              </span>

              <span>
                {item.label}
              </span>
            </Link>
          );
        })}
      </nav>
    </aside>
  );
}