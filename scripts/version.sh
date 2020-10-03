last_version=$( git describe --tags | sed -ne 's/[^0-9]*\(\([0-9]\.\)\{0,4\}[0-9][^.]\).*/\1/p'| sed 's/.$//')
IFS='.'
read -ra parts <<< "$last_version"
p=()
count=0
for i in "${parts[@]}"; do # access each element of array
    p[$count]=$i;
    ((count=count+1));	
done

major=${p[0]}
minor=${p[1]}
bugfix=${p[2]}
if [ $bugfix -gt 98 ]
then
  ((bugfix=0))

  if [ $minor -gt 98 ]
  then
    ((minor=0))
    ((major=major+1))
  else
    ((minor=minor+1)) 	
  fi
else
  ((bugfix=bugfix+1))	
fi

new_version="$major.$minor.$bugfix"
IFS=''
set-env NEW_VERSION="$new_version"

