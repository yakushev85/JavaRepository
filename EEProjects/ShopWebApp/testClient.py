import requests

respSignUp = requests.post("http://localhost:8080/signup", data='{"username": "oleks","password": "1234"}', headers={'Accept': 'application/json', 'Content-Type': 'application/json'})
print("\n\nSignup endpoint response\n ", respSignUp, "\nHeaders:", respSignUp.headers, "\nText:", respSignUp.text)

respLogin = requests.post("http://localhost:8080/login", data='{"username": "oleks","password": "1234"}', headers={'Accept': 'application/json', 'Content-Type': 'application/json'})
print("\n\nLogin endpoint response\n ", respLogin, "\nHeaders:", respLogin.headers, "\nText:", respLogin.text)

respGet = requests.get("http://localhost:8080/users/username/oleks", headers={'Accept': 'application/json', 'Content-Type': 'application/json', 'Access-Control-Expose-Headers': 'x-csrf-token', 'x-csrf-token': respLogin.headers['x-csrf-token']})
print("\n\nGet-User endpoint response: ", respGet, "\nHeaders:", respGet.headers, "\nText:", respGet.text)
