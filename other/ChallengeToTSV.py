import json

with open("Challenge.json","r") as f:
	dataDict = json.load(f)

results = dataDict["results"]
finalString = "category\tcreatedAt\tdescription\thashTag\tobjectId\tpoints\tupdatedAt\n"

for challenge in results:
	category = challenge.get("category", {}).get("objectId", "")
	createdAt = challenge.get("createdAt", "")
	description = challenge.get("description", "")
	hashTag = challenge.get("hashTag", "")
	objectId = challenge.get("objectId", "")
	points = challenge.get("points", "")
	updatedAt = challenge.get("updatedAt", "")
	finalString += "%s\t%s\t%s\t%s\t%s\t%s\t%s\n" % (category,createdAt,description,hashTag,objectId,points,updatedAt)

with open("Challenge.tsv", "w") as f:
	f.write(finalString.encode("utf-8"))
