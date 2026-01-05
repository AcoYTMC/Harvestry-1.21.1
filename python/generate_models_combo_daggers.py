import os
import json

output_folder = "output"
model_folder = "models"
os.makedirs(model_folder, exist_ok=True)

json_names = []

for filename in os.listdir(output_folder):
    if not filename.endswith(".png"):
        continue

    name = filename[:-4]  # remove .png extension
    model_data = {
        "parent": "item/generated",
        "textures": {
            "layer0": f"harvestry:item/{name}"
        }
    }

    json_path = os.path.join(model_folder, f"{name}.json")
    with open(json_path, "w") as f:
        json.dump(model_data, f, indent=4)

    json_names.append(f"Harvestry.id('{name}')")

print("generated JSON files:")
for n in json_names:
    print(n)
