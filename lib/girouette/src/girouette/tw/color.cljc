(ns girouette.tw.color
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [girouette.tw.common :refer [value-unit->css div-100 mul-255 clamp-0-255]]
            [garden.color :as gc]))

;; Those color values and names are from Tailwind CSS.
;; They are imported here for the purpose of compatibility.
(def ^:no-doc default-colors
  {"rose" {"50" "fff1f2"
           "100" "ffe4e6"
           "200" "fecdd3"
           "300" "fda4af"
           "400" "fb7185"
           "500" "f43f5e"
           "600" "e11d48"
           "700" "be123c"
           "800" "9f1239"
           "900" "881337"}

   "pink" {"50" "fdf2f8"
           "100" "fce7f3"
           "200" "fbcfe8"
           "300" "f9a8d4"
           "400" "f472b6"
           "500" "ec4899"
           "600" "db2777"
           "700" "be185d"
           "800" "9d174d"
           "900" "831843"}

   "fuchsia" {"50" "fdf4ff"
              "100" "fae8ff"
              "200" "f5d0fe"
              "300" "f0abfc"
              "400" "e879f9"
              "500" "d946ef"
              "600" "c026d3"
              "700" "a21caf"
              "800" "86198f"
              "900" "701a75"}

   "purple" {"50" "faf5ff"
             "100" "f3e8ff"
             "200" "e9d5ff"
             "300" "d8b4fe"
             "400" "c084fc"
             "500" "a855f7"
             "600" "9333ea"
             "700" "7e22ce"
             "800" "6b21a8"
             "900" "581c87"}
 
   "violet" {"50" "f5f3ff"
             "100" "ede9fe"
             "200" "ddd6fe"
             "300" "c4b5fd"
             "400" "a78bfa"
             "500" "8b5cf6"
             "600" "7c3aed"
             "700" "6d28d9"
             "800" "5b21b6"
             "900" "4c1d95"}
 
   "indigo" {"50" "eef2ff"
             "100" "e0e7ff"
             "200" "c7d2fe"
             "300" "a5b4fc"
             "400" "818cf8"
             "500" "6366f1"
             "600" "4f46e5"
             "700" "4338ca"
             "800" "3730a3"
             "900" "312e81"}

   "blue" {"50" "eff6ff"
           "100" "dbeafe"
           "200" "bfdbfe"
           "300" "93c5fd"
           "400" "60a5fa"
           "500" "3b82f6"
           "600" "2563eb"
           "700" "1d4ed8"
           "800" "1e40af"
           "900" "1e3a8a"}
 
   "lightBlue" {"50" "f0f9ff"
                "100" "e0f2fe"
                "200" "bae6fd"
                "300" "7dd3fc"
                "400" "38bdf8"
                "500" "0ea5e9"
                "600" "0284c7"
                "700" "0369a1"
                "800" "075985"
                "900" "0c4a6e"}
 
   "cyan" {"50" "ecfeff"
           "100" "cffafe"
           "200" "a5f3fc"
           "300" "67e8f9"
           "400" "22d3ee"
           "500" "06b6d4"
           "600" "0891b2"
           "700" "0e7490"
           "800" "155e75"
           "900" "164e63"}
 
   "teal" {"50" "f0fdfa"
           "100" "ccfbf1"
           "200" "99f6e4"
           "300" "5eead4"
           "400" "2dd4bf"
           "500" "14b8a6"
           "600" "0d9488"
           "700" "0f766e"
           "800" "115e59"
           "900" "134e4a"}
 
   "emerald" {"50" "ecfdf5"
              "100" "d1fae5"
              "200" "a7f3d0"
              "300" "6ee7b7"
              "400" "34d399"
              "500" "10b981"
              "600" "059669"
              "700" "047857"
              "800" "065f46"
              "900" "064e3b"}
 
   "green" {"50" "f0fdf4"
            "100" "dcfce7"
            "200" "bbf7d0"
            "300" "86efac"
            "400" "4ade80"
            "500" "22c55e"
            "600" "16a34a"
            "700" "15803d"
            "800" "166534"
            "900" "14532d"}
 
   "lime" {"50" "f7fee7"
           "100" "ecfccb"
           "200" "d9f99d"
           "300" "bef264"
           "400" "a3e635"
           "500" "84cc16"
           "600" "65a30d"
           "700" "4d7c0f"
           "800" "3f6212"
           "900" "365314"}
 
   "yellow" {"50" "fefce8"
             "100" "fef9c3"
             "200" "fef08a"
             "300" "fde047"
             "400" "facc15"
             "500" "eab308"
             "600" "ca8a04"
             "700" "a16207"
             "800" "854d0e"
             "900" "713f12"}
 
   "amber" {"50" "fffbeb"
            "100" "fef3c7"
            "200" "fde68a"
            "300" "fcd34d"
            "400" "fbbf24"
            "500" "f59e0b"
            "600" "d97706"
            "700" "b45309"
            "800" "92400e"
            "900" "78350f"}
 
   "orange" {"50" "fff7ed"
             "100" "ffedd5"
             "200" "fed7aa"
             "300" "fdba74"
             "400" "fb923c"
             "500" "f97316"
             "600" "ea580c"
             "700" "c2410c"
             "800" "9a3412"
             "900" "7c2d12"}
 
   "red" {"50" "fef2f2"
          "100" "fee2e2"
          "200" "fecaca"
          "300" "fca5a5"
          "400" "f87171"
          "500" "ef4444"
          "600" "dc2626"
          "700" "b91c1c"
          "800" "991b1b"
          "900" "7f1d1d"}
 
   "warmGray" {"50" "fafaf9"
               "100" "f5f5f4"
               "200" "e7e5e4"
               "300" "d6d3d1"
               "400" "a8a29e"
               "500" "78716c"
               "600" "57534e"
               "700" "44403c"
               "800" "292524"
               "900" "1c1917"}
 
   "trueGray" {"50" "fafafa"
               "100" "f5f5f5"
               "200" "e5e5e5"
               "300" "d4d4d4"
               "400" "a3a3a3"
               "500" "737373"
               "600" "525252"
               "700" "404040"
               "800" "262626"
               "900" "171717"}
 
   "gray" {"50" "fafafa"
           "100" "f4f4f5"
           "200" "e4e4e7"
           "300" "d4d4d8"
           "400" "a1a1aa"
           "500" "71717a"
           "600" "52525b"
           "700" "3f3f46"
           "800" "27272a"
           "900" "18181b"}
 
   "coolGray" {"50" "f9fafb"
               "100" "f3f4f6"
               "200" "e5e7eb"
               "300" "d1d5db"
               "400" "9ca3af"
               "500" "6b7280"
               "600" "4b5563"
               "700" "374151"
               "800" "1f2937"
               "900" "111827"}
 
   "blueGray" {"50" "f8fafc"
               "100" "f1f5f9"
               "200" "e2e8f0"
               "300" "cbd5e1"
               "400" "94a3b8"
               "500" "64748b"
               "600" "475569"
               "700" "334155"
               "800" "1e293b"
               "900" "0f172a"}})


(let [default-color-names (->> (keys default-colors)
                               (map (fn [color] (str "'" color "'")))
                               (str/join " | "))]
  (def ^:no-doc color-rules (str "
  color = color-rgb | color-rgba |
          color-hsl | color-hsla |
          default-color-single-name |
          default-color-darkness-opacity

  color-rgb = (<'#'> | <'rgb-'>) (#'[0-9a-f]{3}' | #'[0-9a-f]{6}')
  color-rgba = (<'#'> | <'rgba-'>) (#'[0-9a-f]{4}' | #'[0-9a-f]{8}')

  color-hsl = <'hsl-'> color-hue <'-'> color-saturation <'-'> color-lightness
  color-hsla = <'hsla-'> color-hue <'-'> color-saturation <'-'> color-lightness <'-'> color-opacity
  <color-hue> = number
  <color-saturation> = number
  <color-lightness> = number
  <color-opacity> = number

  default-color-single-name = 'transparent' | 'current' | 'black' | 'white'
  default-color-darkness-opacity = (" default-color-names ")

                                   (* Darkness *)
                                    <'-'> ('50' | '100' | '200' | '300' | '400' |
                                           '500' | '600' | '700' | '800' | '900')

                                   (* Opacity *)
                                   (<'-'> integer)?
")))

(def ^:private zero-to-f "0123456789abcdef")

(defn- read-hex-value [hex-value]
  (edn/read-string (str "0x" hex-value)))

(defn- byte->hex [byte]
  (when byte
    (str (nth zero-to-f (bit-and (bit-shift-right byte 4) 0xf))
         (nth zero-to-f (bit-and byte 0xf)))))

(defn read-color
  "Returns a 4-elements vector containing the color components, or a string.
   The nil value is returned if a particular component is not specified in the input."
  [color]
  (let [[_ [type param1 param2 param3 param4]] color]
    (case type
      :color-rgb (case (count param1)
                   3 [(read-hex-value (str (nth param1 0) (nth param1 0)))
                      (read-hex-value (str (nth param1 1) (nth param1 1)))
                      (read-hex-value (str (nth param1 2) (nth param1 2)))
                      nil]
                   6 [(read-hex-value (subs param1 0 2))
                      (read-hex-value (subs param1 2 4))
                      (read-hex-value (subs param1 4 6))
                      nil])

      :color-rgba (case (count param1)
                    4 [(read-hex-value (str (nth param1 0) (nth param1 0)))
                       (read-hex-value (str (nth param1 1) (nth param1 1)))
                       (read-hex-value (str (nth param1 2) (nth param1 2)))
                       (read-hex-value (str (nth param1 3) (nth param1 3)))]
                    8 [(read-hex-value (subs param1 0 2))
                       (read-hex-value (subs param1 2 4))
                       (read-hex-value (subs param1 4 6))
                       (read-hex-value (subs param1 6 8))])

      (:color-hsl :color-hsla) (let [{:keys [red green blue]} (gc/hsl->rgb {:hue (value-unit->css param1)
                                                                            :saturation (value-unit->css param2)
                                                                            :lightness (value-unit->css param3)})
                                     alpha (when (some? param4)
                                             (value-unit->css param4 {:value-fn (comp clamp-0-255 int div-100 mul-255)}))]
                                 [red green blue alpha])

      :default-color-single-name ({"transparent" "transparent"
                                   "current" "currentColor"
                                   "black" [0 0 0 nil]
                                   "white" [255 255 255 nil]} param1)

      :default-color-darkness-opacity
      (let [color-name param1
            darkness param2
            color-code (get-in default-colors [color-name darkness])
            r (read-hex-value (subs color-code 0 2))
            b (read-hex-value (subs color-code 2 4))
            g (read-hex-value (subs color-code 4 6))
            a (when (some? param3)
                (value-unit->css param3 {:value-fn (comp clamp-0-255 int div-100 mul-255)}))]
          [r b g a]))))

(defn as-transparent
  "Transforms a color into a transparent one when possible."
  [color]
  (if (string? color)
    color
    (let [[r g b _] color]
      [r g b 0])))

(defn color->css
  "Transform a color structure into a css string."
  [color]
  (if (string? color)
    color
    (let [[r g b a] color]
      (if (string? a)
        (str "rgba("
              r ","
              g ","
              b ","
              a ")")
        (str "#"
             (byte->hex r)
             (byte->hex g)
             (byte->hex b)
             (byte->hex a))))))
