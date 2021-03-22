#!/bin/bash
cd ../Simulation-Configs
i=1

for filename in *; do
  while IFS= read -r line
  do
    if [ "$i" -eq "1" ]; then
      args1="${line#*:}"
    fi

    if [ "$i" -eq "2" ]; then
      args2="${line#*:}"
    fi

    if [ "$i" -eq "3" ]; then
      args3="${line#*:}"
    fi

    if [ "$i" -eq "4" ]; then
      args4="${line#*:}"
    fi

    if [ "$i" -eq "5" ]; then
      args5="${line#*:}"
    fi

    if [ "$i" -eq "6" ]; then
      args6="${line#*:}"
    fi

    if [ "$i" -eq "7" ]; then
      args7="${line#*:}"
    fi

    ((i=i+1))

  done < "$filename"

  python3 ../Dataset-Creation/NormalDistributionCreation.py "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7"

  # shellcheck disable=SC2164
  cd ../../src

  javac Simulation.java
  java Simulation "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7"

  # shellcheck disable=SC2164
  cd ../Scripting/Simulation-Configs

  ((i=i-7))
done