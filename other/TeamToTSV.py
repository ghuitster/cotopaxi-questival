import json

with open("Team.json","r") as f:
	dataDict = json.load(f)

results = dataDict["results"]
finalString = "createdAt\tname\tobjectId\tpoints\tupdatedAt\n"

for team in results:
	createdAt = team.get("createdAt", "never")
	name = team.get("name", "NO NAME")
	objectId = team.get("objectId", "NO ID")
	points = team.get("points", "0")
	updatedAt = team.get("updatedAt", "never")
	finalString += "%s\t%s\t%s\t%s\t%s\n" % (createdAt, name, objectId, points, updatedAt)

with open("Team.tsv", "w") as f:
	f.write(finalString.encode("utf-8"))
