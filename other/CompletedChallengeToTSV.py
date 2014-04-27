import json

with open("CompletedChallenge.json","r") as f:
	dataDict = json.load(f)

results = dataDict["results"]
finalString = "createdAt\tchallenge\tobjectId\tuser\tupdatedAt\tteam\n"

for completedChallenge in results:
	createdAt = completedChallenge.get("createdAt", "")
	challenge = completedChallenge.get("challenge", {}).get("objectId", "")
	objectId = completedChallenge.get("objectId", "")
	user = completedChallenge.get("user", {}).get("objectId", "")
	updatedAt = completedChallenge.get("updatedAt", "")
	team = completedChallenge.get("team", {}).get("objectId", "")
	finalString += "%s\t%s\t%s\t%s\t%s\t%s\n" % (createdAt, challenge, objectId, user, updatedAt, team)

with open("CompletedChallenge.tsv", "w") as f:
	f.write(finalString.encode("utf-8"))
