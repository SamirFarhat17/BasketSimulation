input="../Simulation-Configs/Standard.txt"
while IFS= read -r line
do
  echo "$line"
done < "$input"