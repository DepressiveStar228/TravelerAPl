POST {{host}}/api/travel-plans
{
  "title": "Conflict Test Plan"
}

HTTP 201
[Captures]
plan_id: jsonpath "$.id"

POST {{host}}/api/travel-plans/{{plan_id}}/locations
{
    "name": "Test Location",
    "budget": 100
}

HTTP 201
[Captures]
loc_id: jsonpath "$.id"
loc_version: jsonpath "$.version"

PUT {{host}}/api/locations/{{loc_id}}
{
    "name": "Updated by User A",
    "version": {{loc_version}}
}

HTTP 200
[Asserts]
jsonpath "$.version" == 2

PUT {{host}}/api/locations/{{loc_id}}
{
    "name": "Updated by User B",
    "version": {{loc_version}}
}

HTTP 409
[Asserts]
jsonpath "$.error" contains "Conflict"