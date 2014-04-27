import json
from cgi import escape

with open("Pledge.json","r") as f:
	dataDict = json.load(f)

results = dataDict["results"]
finalString = "content\tcreatedAt\thashTag\tobjectId\tupdatedAt\tuser\n"

for pledge in results:
	content = pledge.get("content", "NO CONTENT").replace("\n", " ")
	createdAt = pledge.get("createdAt", "never")
	hashTag = pledge.get("hashTag", "NO HASHTAG")
	objectId = pledge.get("objectId", "NO ID")
	updatedAt = pledge.get("updatedAt", "never")
	user = pledge.get("user", {}).get("objectId", "")
	finalString += "%s\t%s\t%s\t%s\t%s\t%s\n" % (content,createdAt,hashTag,objectId,updatedAt,user)

with open("Pledge.tsv", "w") as f:
	f.write(finalString.encode("utf-8"))
